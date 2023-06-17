package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserDeviceEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraUserDeviceRepository: CoroutineCrudRepository<CassandraUserDeviceEntity, String> {
    suspend fun findAllByUserId(userId: String): List<CassandraUserDeviceEntity>
    suspend fun findFirstByUserIdAndFcmToken(userId: String, fcmToken: String): CassandraUserDeviceEntity?
    suspend fun deleteAllByFcmToken(fcmToken: String)
}
