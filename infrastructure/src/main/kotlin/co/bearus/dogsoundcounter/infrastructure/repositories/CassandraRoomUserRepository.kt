package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomUserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraRoomUserRepository : CoroutineCrudRepository<CassandraRoomUserEntity, String> {
    suspend fun findAllByRoomId(roomId: String): List<CassandraRoomUserEntity>
    suspend fun findAllByUserId(userId: String): List<CassandraRoomUserEntity>
    suspend fun findByRoomIdAndUserId(roomId: String, userId: String): CassandraRoomUserEntity
}
