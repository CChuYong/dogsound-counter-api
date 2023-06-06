package co.bearus.dogsoundcounter.infrastructure.clients

import co.bearus.dogsoundcounter.entities.OAuthResult
import co.bearus.dogsoundcounter.entities.UserProvider
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthGateway
import com.fasterxml.jackson.databind.ObjectMapper
import com.nimbusds.jose.jwk.JWK
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Component
class AppleOAuthGateway(
    @Qualifier("appleOAuthClient") private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
) : OAuthGateway {
    override suspend fun authenticate(token: String): OAuthResult {
        return webClient.get()
            .uri("auth/keys")
            .headers {
                it.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
            }
            .retrieve()
            .bodyToMono(KeyResponse::class.java)
            .map {
                val header = token.split("\\.".toRegex()).toTypedArray()[0]
                val decodedHeader = String(Base64.getDecoder().decode(header))
                val parsedHeader = objectMapper.readValue(decodedHeader, JWTTokenHeader::class.java)
                val key = it?.keys?.first() { it.kid == parsedHeader.kid }
                val keyData = JWK.parse(ObjectMapper().writeValueAsString(key)).toRSAKey().toRSAPublicKey()
                val parsed = Jwts.parserBuilder()
                    .setSigningKey(keyData)
                    .build()
                    .parseClaimsJws(token)

                OAuthResult(
                    id = parsed.body["sub"] as String,
                    email = parsed.body["email"] as String,
                    provider = UserProvider.APPLE,
                )
            }.awaitSingle()
    }

    data class Key(val kty: String, val kid: String, val use: String, val alg: String, val n: String, val e: String)
    data class KeyResponse(
        val keys: List<Key>,
    )

    data class JWTTokenHeader(var kid: String, val alg: String)
}
