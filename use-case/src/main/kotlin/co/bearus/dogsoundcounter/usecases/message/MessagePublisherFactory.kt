package co.bearus.dogsoundcounter.usecases.message

interface MessagePublisherFactory {
    fun getSuitableFactory(userId: String): MessagePublisher
}
