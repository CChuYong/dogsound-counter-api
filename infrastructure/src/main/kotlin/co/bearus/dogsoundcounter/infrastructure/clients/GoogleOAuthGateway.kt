package co.bearus.dogsoundcounter.infrastructure.clients

import co.bearus.dogsoundcounter.entities.OAuthResult
import co.bearus.dogsoundcounter.entities.UserProvider
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthGateway
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GoogleOAuthGateway(
    @Qualifier("googleOAuthClient") private val webClient: WebClient,
) : OAuthGateway {
    override suspend fun authenticate(token: String): OAuthResult {
        return webClient.get()
            .uri("/v1/userinfo")
            .headers {
                it.set("Authorization", "Bearer $token")
            }
            .retrieve()
            .bodyToMono(GoogleUserInfo::class.java)
            .map {
                OAuthResult(
                    id = it.sub,
                    email = it.email,
                    name = it.name,
                    provider = UserProvider.GOOGLE,
                )
            }
            .awaitSingle()
    }

    data class GoogleUserInfo(
        val sub: String,
        val name: String?,
        val email: String,
    )
}
