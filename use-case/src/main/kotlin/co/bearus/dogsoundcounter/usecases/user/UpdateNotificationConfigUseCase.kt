package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.UserNotificationConfig
import co.bearus.dogsoundcounter.usecases.UseCase

class UpdateNotificationConfigUseCase(
    private val userNotificationRepository: UserNotificationRepository,
): UseCase<UpdateNotificationConfigUseCase.Input, UserNotificationConfig> {
    data class Input(
        val user: User,
        val type: String,
        val value: Boolean
    )

    override suspend fun execute(input: Input): UserNotificationConfig {
        var notification = userNotificationRepository.getByUserId(input.user.userId)
        when(input.type) {
            "socialAlert" -> notification = notification.copy(socialAlert = input.value)
            "badSoundAlert" -> notification = notification.copy(badSoundAlert = input.value)
            "nonBadSoundAlert" -> notification = notification.copy(nonBadSoundAlert = input.value)
            "noticeAlert" -> notification = notification.copy(noticeAlert = input.value)
        }
        return userNotificationRepository.persist(notification)
    }
}
