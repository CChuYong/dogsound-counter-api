package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomUserPriceEntity
import org.springframework.data.cassandra.repository.AllowFiltering
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraRoomUserPriceRepository : CoroutineCrudRepository<CassandraRoomUserPriceEntity, String> {
    suspend fun findFirstByRoomUserIdAndStartDay(roomUserId: String, startDay: String): CassandraRoomUserPriceEntity?
    suspend fun findAllByRoomUserId(roomUserId: String): List<CassandraRoomUserPriceEntity>

    @AllowFiltering
    suspend fun findAllByRoomUserIdAndStartDay(roomUserId: String, startDay: String): List<CassandraRoomUserPriceEntity>

    @Query("SELECT SUM(cumulated_price) FROM room_user_price WHERE room_user_id = ?0")
    suspend fun sumOfPriceByRoomUserId(roomUserId: String): Long
}
