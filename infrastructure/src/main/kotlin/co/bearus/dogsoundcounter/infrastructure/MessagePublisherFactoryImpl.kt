package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.usecases.message.MessagePublisher
import co.bearus.dogsoundcounter.usecases.message.MessagePublisherFactory
import org.springframework.stereotype.Component

@Component
class MessagePublisherFactoryImpl(
    private val kafkaMessagePublisher: KafkaMessagePublisher,
    private val firebaseMessagePublisher: FirebaseMessagePublisher,
) : MessagePublisherFactory {
    override fun getSuitableFactory(userId: String): MessagePublisher {
        //TODO: IF ONLINE -> KAFKA // ELSE -> PUSH
        return firebaseMessagePublisher
    }
}
