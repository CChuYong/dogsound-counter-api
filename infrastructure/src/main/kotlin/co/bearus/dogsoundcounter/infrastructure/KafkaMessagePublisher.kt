package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.MessagePublisher
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions

@Component
class KafkaMessagePublisher(
    private val objectMapper: ObjectMapper,
): MessagePublisher{
    val producerOpts = SenderOptions.create<Int, String>(getProps())
        .maxInFlight(1024).let {
            KafkaSender.create(it)
        }

    private fun getProps(): Map<String, Any> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to listOf("kafka-headless.kafka.svc.cluster.local:9092"),
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to IntegerDeserializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
    )

    override fun publishMessage(userId: String, message: ClientPacket): Mono<Boolean> {
        val data = objectMapper.writeValueAsString(message)
        return producerOpts.createOutbound()
            .send(Mono.just(ProducerRecord("messages:$userId", data)))
            .then()
            .map { true }
            .onErrorResume { Mono.just(false) }
    }
}
