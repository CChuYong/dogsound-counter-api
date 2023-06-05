package co.bearus.dogsoundcounter.infrastructure.clients

import co.bearus.dogsoundcounter.entities.OAuthResult
import co.bearus.dogsoundcounter.entities.UserProvider
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthGateway
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoOAuthGateway(
    @Qualifier("kakaoOAuthClient") private val webClient: WebClient,
) : OAuthGateway {
    override suspend fun authenticate(token: String): OAuthResult {
        return webClient.post()
            .uri("v2/user/me")
            .headers {
                it.set("Authorization", "Bearer $token")
                it.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
            }
            .retrieve()
            .bodyToMono(KakaoUserResponse::class.java)
            .map {
                OAuthResult(
                    id = it.id.toString(),
                    email = it.kakao_account?.email ?: "[UNVERIFIED]",
                    provider = UserProvider.KAKAO,
                )
            }.awaitSingle()
    }

    data class KakaoUserResponse(
        val id: Long,
        val connected_at: String,
        val kakao_account: KakaoEmailAccount?,
    )

    data class KakaoEmailAccount(
        val email: String?,
        val email_needs_agreement: Boolean?,
        val is_email_valid: Boolean?,
        val is_email_verified: Boolean?,
    )
}