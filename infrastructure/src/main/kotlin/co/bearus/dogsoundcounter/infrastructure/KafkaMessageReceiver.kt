package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.message.MessageReceiver
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions

@Component
class KafkaMessageReceiver(
    private val objectMapper: ObjectMapper,
    @Value("\${app.kafka.servers}") private val bootstrapUrls: String,
) : MessageReceiver {
    override fun createChannel(userId: String, deviceId: String): Flux<ClientPacket> {
        val receiverOpts = ReceiverOptions.create<Int, String>(getProps(deviceId))
            .subscription(listOf("events-$userId"))

        return KafkaReceiver.create(receiverOpts)
            .receive()
            .map {
                objectMapper.readValue(it.value(), ClientPacket::class.java)
            }
    }

    private fun getProps(groupId: String): Map<String, Any> = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to listOf(bootstrapUrls),
        ConsumerConfig.GROUP_ID_CONFIG to groupId,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to IntegerDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
    )
}
