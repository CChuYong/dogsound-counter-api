package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.AlreadyFriendException
import co.bearus.dogsoundcounter.entities.exception.user.FriendCannotBeMyselfException
import co.bearus.dogsoundcounter.usecases.UseCase
import co.bearus.dogsoundcounter.usecases.notification.Notification
import co.bearus.dogsoundcounter.usecases.notification.NotificationGateway

class CreateNewFriendUseCase(
    private val friendRepository: FriendRepository,
    private val userNotificationRepository: UserNotificationRepository,
    private val notificationGateway: NotificationGateway,
    private val userDeviceRepository: UserDeviceRepository,
) : UseCase<CreateNewFriendUseCase.Input, CreateNewFriendUseCase.Output> {
    data class Input(
        val user1: User,
        val user2: User,
    )

    data class Output(
        val user1: User,
        val user2: User,
    )

    override suspend fun execute(input: Input): Output {
        if (input.user1.userId == input.user2.userId) throw FriendCannotBeMyselfException()
        if (friendRepository.isFriend(input.user1.userId, input.user2.userId)) throw AlreadyFriendException()

        friendRepository.sendRequest(input.user1.userId, input.user2.userId)
        val config = userNotificationRepository.getByUserId(input.user2.userId)
        if (config.socialAlert) {
            val device = userDeviceRepository.getUserDevices(input.user2.userId)
            val notification = Notification(
                title = "친구 요청이 도착했어요!",
                body = "${input.user1.nickname}으로부터 친구 요청이 도착했어요!",
            )
            notificationGateway.sendMulti(device.map { it.fcmToken }, notification)
        }

        return Output(
            user1 = input.user1,
            user2 = input.user2,
        )
    }
}
