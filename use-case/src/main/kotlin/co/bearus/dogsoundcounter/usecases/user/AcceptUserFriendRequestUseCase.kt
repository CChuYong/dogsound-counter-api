package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.AlreadyFriendException
import co.bearus.dogsoundcounter.entities.exception.user.FriendRequestNotValidException
import co.bearus.dogsoundcounter.usecases.UseCase
import co.bearus.dogsoundcounter.usecases.notification.Notification
import co.bearus.dogsoundcounter.usecases.notification.NotificationGateway

class AcceptUserFriendRequestUseCase(
    private val friendRepository: FriendRepository,
    private val userNotificationRepository: UserNotificationRepository,
    private val userDeviceRepository: UserDeviceRepository,
    private val notificationGateway: NotificationGateway,
) : UseCase<AcceptUserFriendRequestUseCase.Input, AcceptUserFriendRequestUseCase.Output> {
    data class Input(
        val from: User,
        val target: User,
    )

    data class Output(
        val user1: User,
        val user2: User,
    )

    override suspend fun execute(input: Input): Output {
        if (!friendRepository.clearRequest(
                input.target.userId,
                input.from.userId
            )
        ) throw FriendRequestNotValidException()
        if (friendRepository.isFriend(input.target.userId, input.from.userId)) throw AlreadyFriendException()
        friendRepository.makeFriend(input.from.userId, input.target.userId)

        val config = userNotificationRepository.getByUserId(input.target.userId)
        if (config.socialAlert) {
            val device = userDeviceRepository.getUserDevices(input.target.userId)
            val notification = Notification(
                title = "친구가 되었어요!",
                body = "${input.from.nickname}님과 친구가 되었어요!",
            )
            notificationGateway.sendMulti(device.map { it.fcmToken }, notification)
        }
        return Output(
            user1 = input.from,
            user2 = input.target,
        )
    }
}
