package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.ClientPacket
import reactor.core.publisher.Flux

interface MessageReceiver {
    fun createChannel(userId: String, deviceId: String): Flux<ClientPacket>
}
