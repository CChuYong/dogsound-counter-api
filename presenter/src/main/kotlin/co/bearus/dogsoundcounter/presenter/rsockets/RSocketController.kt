package co.bearus.dogsoundcounter.presenter.rsockets

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.MessageReceiver
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux

@Controller
class RSocketController(
    private val messageReceiver: MessageReceiver,
) {
    @MessageMapping("users")
    fun openChannel(request: RSocketSubscribeRequest): Flux<ClientPacket> {
        return messageReceiver.createChannel(request.userId)
    }

    data class RSocketSubscribeRequest(
        val userId: String,
    )
}
