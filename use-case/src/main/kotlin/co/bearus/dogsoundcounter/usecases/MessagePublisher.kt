package co.bearus.dogsoundcounter.usecases

import co.bearus.dogsoundcounter.entities.ClientPacket
import reactor.core.publisher.Mono

interface MessagePublisher {
    suspend fun publishMessage(userId: String, message: ClientPacket): Boolean
}
