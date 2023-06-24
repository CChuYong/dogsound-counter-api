package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.TopicManager
import co.bearus.dogsoundcounter.usecases.message.CreateNewMessageUseCase
import co.bearus.dogsoundcounter.usecases.message.GetMessagesByRoomUseCase
import co.bearus.dogsoundcounter.usecases.message.MessagePublisherFactory
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
import co.bearus.dogsoundcounter.usecases.notification.NotificationGateway
import co.bearus.dogsoundcounter.usecases.room.*
import co.bearus.dogsoundcounter.usecases.user.*
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.OAuthFactory
import co.bearus.dogsoundcounter.usecases.user.oauth.SocialLoginRepository
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import co.bearus.dogsoundcounter.usecases.violent.*
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
        userNotificationRepository: UserNotificationRepository,
        topicManager: TopicManager,
    ) = AuthUserWithProviderUseCase(
        oAuthFactory,
        socialLoginRepository,
        userRepository,
        identityGenerator,
        tokenProvider,
        userNotificationRepository,
        topicManager,
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
        roomUserPriceRepository: RoomUserPriceRepository,
    ) = CreateNewMessageUseCase(
        identityGenerator,
        messageRepository,
        notificationGateway,
        userDeviceRepository,
        messagePublisherFactory,
        roomUserPriceRepository,
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
        userRepository: RoomUserPriceRepository,
        roomUserRepository: RoomUserRepository,
    ) = GetUserDashboardUseCase(
        userRepository,
        roomUserRepository,
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

    @Bean
    fun getUserByTag(
        userRepository: UserRepository,
    ) = GetUserByTagUseCase(
        userRepository,
    )

    @Bean
    fun createNewFriend(
        friendRepository: FriendRepository,
    ) = CreateNewFriendUseCase(
        friendRepository,
    )

    @Bean
    fun deleteViolent(
        violentRepository: ViolentRepository,
    ) = DeleteViolentUseCase(
        violentRepository,
    )

    @Bean
    fun updateNotification(
        userNotificationRepository: UserNotificationRepository,
    ) = UpdateNotificationConfigUseCase(
        userNotificationRepository,
    )

    @Bean
    fun acceptFriendRequest(
        friendRepository: FriendRepository,
    ) = AcceptUserFriendRequestUseCase(
        friendRepository,
    )

    @Bean
    fun denyFriendRequest(
        friendRepository: FriendRepository,
    ) = DenyUserFriendRequestUseCase(
        friendRepository,
    )

    @Bean
    fun breakFriend(
        friendRepository: FriendRepository
    ) = BreakFriendUseCase(
        friendRepository,
    )
}
