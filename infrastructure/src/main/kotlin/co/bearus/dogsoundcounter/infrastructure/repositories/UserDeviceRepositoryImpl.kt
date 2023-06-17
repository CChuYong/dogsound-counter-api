package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.UserDevice
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserDeviceEntity
import co.bearus.dogsoundcounter.usecases.user.UserDeviceRepository
import org.springframework.stereotype.Repository

@Repository
class UserDeviceRepositoryImpl(
    private val cassandraUserDeviceRepository: CassandraUserDeviceRepository,
): UserDeviceRepository {
    override suspend fun getUserDevices(userId: String): List<UserDevice> {
        return cassandraUserDeviceRepository.findAllByUserId(userId)
            .map { it.toDomain() }
    }

    override suspend fun findUserDevice(userId: String, fcmToken: String): UserDevice? {
        return cassandraUserDeviceRepository.findFirstByUserIdAndFcmToken(userId, fcmToken)?.toDomain()
    }

    override suspend fun persist(userDevice: UserDevice): UserDevice {
        return cassandraUserDeviceRepository.save(
            CassandraUserDeviceEntity.fromDomain(
                userDevice
            )
        ).toDomain()
    }

    override suspend fun deleteAllByToken(fcmToken: String) {
        cassandraUserDeviceRepository.deleteAllByFcmToken(fcmToken)
    }
}
