package co.bearus.dogsoundcounter.infrastructure.messages

import co.bearus.dogsoundcounter.usecases.message.MessagePublisher
import co.bearus.dogsoundcounter.usecases.message.MessagePublisherFactory
import org.springframework.stereotype.Component

@Component
class MessagePublisherFactoryImpl(
    private val combinedMessagePublisher: CombinedMessagePublisher
) : MessagePublisherFactory {
    override fun getSuitableFactory(userId: String): MessagePublisher {
        //TODO: IF ONLINE -> KAFKA // ELSE -> PUSH
        return combinedMessagePublisher
    }
}
