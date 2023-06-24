package co.bearus.dogsoundcounter.infrastructure.messages

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.message.MessagePublisher
import org.springframework.stereotype.Component

@Component
class CombinedMessagePublisher(
    private val kafkaMessagePublisher: KafkaMessagePublisher,
    private val firebaseMessagePublisher: FirebaseMessagePublisher,
) : MessagePublisher {
    override suspend fun publishMessage(userId: String, message: ClientPacket): Boolean {
        val result = kafkaMessagePublisher.publishMessage(userId, message)
        firebaseMessagePublisher.publishMessage(userId, message)
        return true
    }
}
