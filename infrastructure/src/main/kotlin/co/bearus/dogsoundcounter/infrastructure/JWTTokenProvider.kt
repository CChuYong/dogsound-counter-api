package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JWTTokenProvider(
    private val properties: JWTTokenProperties,
) : TokenProvider {
    val signKey: Key = properties.secret.toByteArray().let { SecretKeySpec(it, SignatureAlgorithm.HS256.jcaName) }
    override suspend fun createAccessToken(user: User): String {
        return Jwts.builder()
            .setHeader(buildHeader())
            .setClaims(buildPayLoad(user))
            .setExpiration(generateAccessTokenExpiration())
            .signWith(signKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override suspend fun createRefreshToken(user: User): String {
        return Jwts.builder()
            .setHeader(buildHeader())
            .setClaims(buildPayLoad(user))
            .setExpiration(generateRefreshTokenExpiration())
            .signWith(signKey, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun buildHeader() =
        mapOf<String, Any>(
            Pair("typ", "JWT"),
            Pair("alg", "HS256"),
            Pair("regDate", System.currentTimeMillis())
        )

    private fun buildPayLoad(user: User) =
        mapOf<String, Any>(
            Pair("id", user.userId)
        )

    private fun generateAccessTokenExpiration() =
        Date(System.currentTimeMillis() + properties.accessTokenValidity * 1000)

    private fun generateRefreshTokenExpiration() =
        Date(System.currentTimeMillis() + properties.refreshTokenValidity * 1000)

}