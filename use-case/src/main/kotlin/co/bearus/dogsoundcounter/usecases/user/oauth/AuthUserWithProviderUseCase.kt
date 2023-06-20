package co.bearus.dogsoundcounter.usecases.user.oauth

import co.bearus.dogsoundcounter.entities.*
import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.UseCase
import co.bearus.dogsoundcounter.usecases.user.UserNotificationRepository
import co.bearus.dogsoundcounter.usecases.user.UserRepository

class AuthUserWithProviderUseCase(
    private val oAuthFactory: OAuthFactory,
    private val socialLoginRepository: SocialLoginRepository,
    private val userRepository: UserRepository,
    private val identityGenerator: IdentityGenerator,
    private val tokenProvider: TokenProvider,
    private val userNotificationRepository: UserNotificationRepository,
) : UseCase<AuthUserWithProviderUseCase.Input, AuthUserWithProviderUseCase.Output> {
    data class Input(
        val provider: UserProvider,
        val token: String,
    )

    data class Output(
        val accessToken: String,
        val refreshToken: String,
    )

    override suspend fun execute(input: Input): Output {
        val gateway = oAuthFactory.of(input.provider)
        val authResult = gateway.authenticate(input.token)
        val socialUser = socialLoginRepository.findByProvider(input.provider, authResult.id)

        // 유저가 쌓기 플로우
        val user = if (socialUser != null) {
            //이미 그 소셜 로그인 유저가 있으면 그냥 그 유저
            userRepository.getById(socialUser.userId)
        } else {
            //소셜로그인 유저가 없으면..
            val previousUser = userRepository.findUserByEmail(authResult.email)
            // But, 1차분기 -> email이 이미 있으면 이 계정에 링킹
            if (previousUser != null) {
                createSocialUser(previousUser, authResult)
                previousUser
            } else {
                // 유저가 없으면 아에 회원가입
                val createdUser = createUser(authResult)
                createSocialUser(createdUser, authResult)
                createdUser
            }
        }

        val accessToken = tokenProvider.createAccessToken(user)
        val refreshToken = tokenProvider.createRefreshToken(user)
        //TODO: SAVE REFRESH TOKEN

        return Output(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    private suspend fun createUser(result: OAuthResult): User {
        val newUser = User.newInstance(
            userId = identityGenerator.createIdentity(),
            email = result.email,
            nickname = result.name,
        )

        val userNotification = UserNotificationConfig.createDefault(newUser.userId)
        userNotificationRepository.persist(userNotification)
        return userRepository.persist(newUser)
    }

    private suspend fun createSocialUser(user: User, result: OAuthResult): SocialLoginUser {
        val newSocialUser = SocialLoginUser.newInstance(
            userId = user.userId,
            provider = result.provider,
            providerKey = result.id,
        )
        return socialLoginRepository.persist(newSocialUser)
    }
}