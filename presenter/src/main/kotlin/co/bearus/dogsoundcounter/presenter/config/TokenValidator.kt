package co.bearus.dogsoundcounter.presenter.config

import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class TokenValidator(
    private val tokenProvider: TokenProvider,
) : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        return Mono.justOrEmpty(exchange)
            .mapNotNull { serverWebExchange ->
                serverWebExchange.request.headers["Authorization"]
            }
            .mapNotNull { headerValues ->
                if (headerValues.isNotEmpty()) {
                    val token = headerValues.first()
                    val userId = tokenProvider.extractUserIdFromToken(token) ?: return@mapNotNull null
                    return@mapNotNull APIKeyAuthentication(
                        userId = userId,
                        token = token,
                    )
                }
                null
            }
    }
}