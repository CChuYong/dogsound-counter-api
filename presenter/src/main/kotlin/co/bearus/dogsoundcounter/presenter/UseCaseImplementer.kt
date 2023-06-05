package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.user.CreateNewUserUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByEmailUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.UserRepository
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthFactory
import co.bearus.dogsoundcounter.usecases.user.oauth.SocialLoginRepository
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class UseCaseImplementer {
    @Bean
    fun createNewUserUseCase(userRepository: UserRepository, identityGenerator: IdentityGenerator) = CreateNewUserUseCase(userRepository, identityGenerator)

    @Bean
    fun getUserByEmailUseCase(userRepository: UserRepository) = GetUserByEmailUseCase(userRepository)

    @Bean
    fun authUserWithProvider(
        oAuthFactory: OAuthFactory,
        socialLoginRepository: SocialLoginRepository,
        userRepository: UserRepository,
        identityGenerator: IdentityGenerator,
        tokenProvider: TokenProvider,
    ) = AuthUserWithProviderUseCase(
        oAuthFactory,
        socialLoginRepository,
        userRepository,
        identityGenerator,
        tokenProvider,
    )

    @Bean
    fun getUserById(
        userRepository: UserRepository,
    ) = GetUserByIdUseCase(
        userRepository,
    )
}
