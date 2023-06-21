package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomUserPriceEntity
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraRoomUserPriceRepository: CoroutineCrudRepository<CassandraRoomUserPriceEntity, String> {
    suspend fun findFirstByRoomUserIdAndStartDay(roomUserId: String, startDay: String): CassandraRoomUserPriceEntity?
    suspend fun findAllByRoomUserId(roomUserId: String): List<CassandraRoomUserPriceEntity>

    @Query("SELECT SUM(cumulated_price) FROM room_user_price WHERE room_user_id = ?0")
    suspend fun sumOfPriceByRoomUserId(roomUserId: String): Long

    @Query("INSERT INTO room_user_price (room_user_id, start_day, user_id, cumulated_price) VALUES (?0, ?1, ?2, 0) IF NOT EXISTS")
    suspend fun insertIfNotExists(roomUserId: String, startDay: String, userId: String)

    @Query("UPDATE room_user_price SET room_user_price = room_user_price + ?0 WHERE room_user_id = ?1 AND start_day = ?2")
    suspend fun addPriceByRoomUserId(price: Int, roomUserId: String, startDay: String)
}