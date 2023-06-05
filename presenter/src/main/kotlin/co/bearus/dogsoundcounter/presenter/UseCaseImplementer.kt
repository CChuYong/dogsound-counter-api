package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.message.CreateNewMessageUseCase
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
import co.bearus.dogsoundcounter.usecases.room.CreateNewRoomUseCase
import co.bearus.dogsoundcounter.usecases.room.GetRoomByIdUseCase
import co.bearus.dogsoundcounter.usecases.room.RoomRepository
import co.bearus.dogsoundcounter.usecases.user.GetUserByEmailUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.UserRepository
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthFactory
import co.bearus.dogsoundcounter.usecases.user.oauth.SocialLoginRepository
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import co.bearus.dogsoundcounter.usecases.violent.CreateNewViolentUseCase
import co.bearus.dogsoundcounter.usecases.violent.GetViolentByIdUseCase
import co.bearus.dogsoundcounter.usecases.violent.ViolentRepository
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class UseCaseImplementer {
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

    @Bean
    fun createNewRoom(
        identityGenerator: IdentityGenerator,
        roomRepository: RoomRepository,
    ) = CreateNewRoomUseCase(
        identityGenerator,
        roomRepository,
    )

    @Bean
    fun getViolentById(
        violentRepository: ViolentRepository,
    ) = GetViolentByIdUseCase(
        violentRepository,
    )

    @Bean
    fun getRoomById(
        roomRepository: RoomRepository,
    ) = GetRoomByIdUseCase(
        roomRepository,
    )

    @Bean
    fun createNewMessage(
        identityGenerator: IdentityGenerator,
        messageRepository: MessageRepository,
    ) = CreateNewMessageUseCase(
        identityGenerator,
        messageRepository,
    )

    @Bean
    fun createNewViolent(
        identityGenerator: IdentityGenerator,
        violentRepository: ViolentRepository,
    ) = CreateNewViolentUseCase(
        identityGenerator,
        violentRepository,
    )
}
