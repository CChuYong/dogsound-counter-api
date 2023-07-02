package co.bearus.dogsoundcounter.presenter.rsockets

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.message.MessageReceiver
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux

@Controller
class RSocketController(
    private val messageReceiver: MessageReceiver,
    private val tokenProvider: TokenProvider,
) {
    val logger = LoggerFactory.getLogger(RSocketController::class.java)
    @MessageMapping("/v1/event-gateway")
    fun openChannel(request: RSocketSubscribeRequest): Flux<ClientPacket> {
        val userId = tokenProvider.extractUserIdFromToken(request.accessToken) ?: throw RuntimeException()
        return messageReceiver.createChannel(userId, request.deviceToken)
    }

    data class RSocketSubscribeRequest(
        val accessToken: String,
        val deviceToken: String,
    )
}
