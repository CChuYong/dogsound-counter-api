package co.bearus.dogsoundcounter.infrastructure.clients

import co.bearus.dogsoundcounter.entities.UserProvider
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthFactory
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthGateway
import org.springframework.stereotype.Component

@Component
class OAuthFactoryImpl(
    private val appleOAuthGateway: AppleOAuthGateway,
    private val kakaoOAuthGateway: KakaoOAuthGateway,
) : OAuthFactory {
    override fun of(userProvider: UserProvider): OAuthGateway {
        return when (userProvider) {
            UserProvider.KAKAO -> kakaoOAuthGateway
            UserProvider.APPLE -> appleOAuthGateway
            else -> TODO()
        }
    }
}