package co.bearus.dogsoundcounter.usecases

interface MessagePublisherFactory {
    fun getSuitableFactory(userId: String): MessagePublisher
}
