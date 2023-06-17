package co.bearus.dogsoundcounter.usecases

import co.bearus.dogsoundcounter.entities.ClientPacket

interface MessagePublisher {
    suspend fun publishMessage(userId: String, message: ClientPacket): Boolean
}
