package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraRoomRepository : CoroutineCrudRepository<CassandraRoomEntity, String> {
    suspend fun getByRoomId(roomId: String): CassandraRoomEntity
}
