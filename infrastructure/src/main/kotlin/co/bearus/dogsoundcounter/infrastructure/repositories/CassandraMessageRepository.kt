package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraMessageEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraMessageRepository : CoroutineCrudRepository<CassandraMessageEntity, String> {
    suspend fun findAllByRoomId(roomId: String): List<CassandraMessageEntity>
    suspend fun findAllByRoomIdAndMessageIdGreaterThan(
        roomId: String,
        messageId: String,
        page: Pageable
    ): List<CassandraMessageEntity>

    suspend fun findAllByRoomIdAndMessageIdLessThan(
        roomId: String,
        messageId: String,
        page: Pageable
    ): List<CassandraMessageEntity>

    suspend fun countByRoomIdAndMessageIdGreaterThan(roomId: String, messageId: String): Long
    suspend fun findFirstByRoomIdOrderByMessageIdDesc(roomId: String): CassandraMessageEntity?
}
