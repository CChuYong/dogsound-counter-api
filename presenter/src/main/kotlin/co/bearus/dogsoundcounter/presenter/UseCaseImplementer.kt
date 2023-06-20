package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.message.MessagePublisherFactory
import co.bearus.dogsoundcounter.usecases.notification.NotificationGateway
import co.bearus.dogsoundcounter.usecases.message.CreateNewMessageUseCase
import co.bearus.dogsoundcounter.usecases.message.GetMessagesByRoomUseCase
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
import co.bearus.dogsoundcounter.usecases.room.*
import co.bearus.dogsoundcounter.usecases.user.*
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthFactory
import co.bearus.dogsoundcounter.usecases.user.oauth.SocialLoginRepository
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import co.bearus.dogsoundcounter.usecases.violent.CreateNewViolentUseCase
import co.bearus.dogsoundcounter.usecases.violent.GetViolentByIdUseCase
import co.bearus.dogsoundcounter.usecases.violent.GetViolentsByRoomUseCase
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
        roomUserRepository: RoomUserRepository,
    ) = CreateNewRoomUseCase(
        identityGenerator,
        roomRepository,
        roomUserRepository,
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
        notificationGateway: NotificationGateway,
        userDeviceRepository: UserDeviceRepository,
        messagePublisherFactory: MessagePublisherFactory,
    ) = CreateNewMessageUseCase(
        identityGenerator,
        messageRepository,
        notificationGateway,
        userDeviceRepository,
        messagePublisherFactory,
    )

    @Bean
    fun createNewViolent(
        identityGenerator: IdentityGenerator,
        violentRepository: ViolentRepository,
    ) = CreateNewViolentUseCase(
        identityGenerator,
        violentRepository,
    )

    @Bean
    fun createNewUser(
        userRepository: UserRepository,
        identityGenerator: IdentityGenerator,
    ) = CreateNewUserUseCase(
        userRepository,
        identityGenerator,
    )

    @Bean
    fun getMessageByRoom(
        messageRepository: MessageRepository,
    ) = GetMessagesByRoomUseCase(
        messageRepository,
    )

    @Bean
    fun getViolentsByRoom(
        violentRepository: ViolentRepository,
    ) = GetViolentsByRoomUseCase(
        violentRepository,
    )

    @Bean
    fun refreshUserWithToken(
        tokenProvider: TokenProvider,
        userRepository: UserRepository,
    ) = RefreshUserWithTokenUseCase(
        tokenProvider,
        userRepository,
    )

    @Bean
    fun getUserDashBoard(
        userRepository: UserRepository,
    ) = GetUserDashboardUseCase(
        userRepository,
    )

    @Bean
    fun getUserRooms(
        roomUserRepository: RoomUserRepository,
        roomRepository: RoomRepository
    ) = GetUserRoomsUseCase(
        roomUserRepository,
        roomRepository,
    )

    @Bean
    fun updateNickname(
        userRepository: UserRepository,
    ) = UpdateNicknameUseCase(
        userRepository,
    )

    @Bean
    fun updateRoomUserLastMessageId(
        roomUserRepository: RoomUserRepository,
    ) = UpdateRoomUserLastMessageIdUseCase(
        roomUserRepository,
    )

    @Bean
    fun createUserDevice(
        userDeviceRepository: UserDeviceRepository,
    ) = CreateUserDeviceUseCase(
        userDeviceRepository,
    )

    @Bean
    fun getRoomUsers(
        roomUserRepository: RoomUserRepository,
        userRepository: UserRepository,
    ) = GetRoomUsersUseCase(
        roomUserRepository, userRepository
    )

    @Bean
    fun updateUserImage(
        userRepository: UserRepository,
    ) = UpdateUserImageUseCase(
        userRepository,
    )

    @Bean
    fun updateRoomImage(
        roomRepository: RoomRepository,
    ) = UpdateRoomImageUseCase(
        roomRepository,
    )
}
