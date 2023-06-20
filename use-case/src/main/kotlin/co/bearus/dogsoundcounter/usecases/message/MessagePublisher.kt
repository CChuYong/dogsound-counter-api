package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.ClientPacket

interface MessagePublisher {
    suspend fun publishMessage(userId: String, message: ClientPacket): Boolean
}
