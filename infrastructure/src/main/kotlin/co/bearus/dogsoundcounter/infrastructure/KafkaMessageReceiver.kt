package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.MessageReceiver
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions

@Component
class KafkaMessageReceiver(
    private val objectMapper: ObjectMapper,
) : MessageReceiver {
    override fun createChannel(userId: String): Flux<ClientPacket> {
        val receiverOpts = ReceiverOptions.create<Int, String>(getProps())
            .subscription(listOf("messages:$userId"))

        return KafkaReceiver.create(receiverOpts)
            .receive()
            .map {
                objectMapper.readValue(it.value(), ClientPacket::class.java)
            }
    }

    private fun getProps(): Map<String, Any> = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to listOf("kafka-headless.kafka.svc.cluster.local:9092"),
        ConsumerConfig.GROUP_ID_CONFIG to "core-group",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to IntegerDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
    )
}
