package co.bearus.dogsoundcounter.infrastructure.messages

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.message.MessagePublisher
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions

@Component
class KafkaMessagePublisher(
    private val objectMapper: ObjectMapper,
    @Value("\${app.kafka.servers}") private val bootstrapUrls: String,
) : MessagePublisher {
    val producerOpts = SenderOptions.create<Int, String>(getProps())
        .maxInFlight(1024).let {
            KafkaSender.create(it)
        }

    private fun getProps(): Map<String, Any> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to listOf(bootstrapUrls),
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to IntegerSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
    )

    override suspend fun publishMessage(userId: String, message: ClientPacket): Boolean {
        val data = objectMapper.writeValueAsString(message)
        return producerOpts.createOutbound()
            .send(Mono.just(ProducerRecord("events-$userId", data)))
            .then()
            .map { true }
            .onErrorResume {
                Mono.just(false)
            }
            .awaitSingleOrNull() ?: false
    }
}
