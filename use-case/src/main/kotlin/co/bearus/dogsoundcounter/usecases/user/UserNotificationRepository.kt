package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.UserNotificationConfig

interface UserNotificationRepository {
    suspend fun getByUserId(userId: String): UserNotificationConfig
    suspend fun persist(userNotificationConfig: UserNotificationConfig): UserNotificationConfig
}