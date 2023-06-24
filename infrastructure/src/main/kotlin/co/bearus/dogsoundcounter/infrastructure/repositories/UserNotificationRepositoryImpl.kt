package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.UserNotificationConfig
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraNotificationConfigEntity
import co.bearus.dogsoundcounter.usecases.user.UserNotificationRepository
import org.springframework.stereotype.Repository

@Repository
class UserNotificationRepositoryImpl(
    private val cassandraNotificationConfigRepository: CassandraNotificationConfigRepository,
) : UserNotificationRepository {
    override suspend fun getByUserId(userId: String): UserNotificationConfig {
        return cassandraNotificationConfigRepository.findById(userId)?.toDomain() ?: throw RuntimeException()
    }

    override suspend fun persist(userNotificationConfig: UserNotificationConfig): UserNotificationConfig {
        return cassandraNotificationConfigRepository.save(
            CassandraNotificationConfigEntity.fromDomain(
                userNotificationConfig
            )
        ).toDomain()
    }
}
