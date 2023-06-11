package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraMessageEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraMessageRepository : CoroutineCrudRepository<CassandraMessageEntity, String> {
    suspend fun findAllByRoomId(roomId: String): List<CassandraMessageEntity>
    suspend fun countByMessageIdGreaterThanAndRoomId(messageId: String, roomId: String): Long
}
