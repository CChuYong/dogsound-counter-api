package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.usecases.MessagePublisher
import co.bearus.dogsoundcounter.usecases.MessagePublisherFactory
import org.springframework.stereotype.Component

@Component
class MessagePublisherFactoryImpl(
    private val kafkaMessagePublisher: KafkaMessagePublisher,
    private val firebaseMessagePublisher: FirebaseMessagePublisher,
): MessagePublisherFactory {
    override fun getSuitableFactory(userId: String): MessagePublisher {
        //TODO: IF ONLINE -> KAFKA // ELSE -> PUSH
        return firebaseMessagePublisher
    }
}
