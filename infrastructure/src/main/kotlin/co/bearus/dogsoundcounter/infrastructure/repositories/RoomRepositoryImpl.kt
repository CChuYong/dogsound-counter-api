package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomEntity
import co.bearus.dogsoundcounter.usecases.room.RoomRepository
import org.springframework.stereotype.Repository

@Repository
class RoomRepositoryImpl(
    private val cassandraRoomRepository: CassandraRoomRepository,
): RoomRepository {
    override suspend fun persist(room: Room): Room {
        return cassandraRoomRepository.save(
            CassandraRoomEntity.fromDomain(room)
        ).toDomain()
    }
}