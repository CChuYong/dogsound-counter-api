package co.bearus.dogsoundcounter.usecases

import co.bearus.dogsoundcounter.entities.ClientPacket
import reactor.core.publisher.Mono

interface MessagePublisher {
    fun publishMessage(userId: String, message: ClientPacket): Mono<Boolean>
}
