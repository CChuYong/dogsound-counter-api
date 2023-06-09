package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.RoomUser
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomUserEntity
import co.bearus.dogsoundcounter.usecases.room.RoomUserRepository
import org.springframework.stereotype.Repository

@Repository
class RoomUserRepositoryImpl(
    private val cassandraRoomUserRepository: CassandraRoomUserRepository,
): RoomUserRepository {
    override suspend fun findByUserId(userId: String): List<RoomUser> {
        return cassandraRoomUserRepository.findAllByUserId(userId).map { it.toDomain() }
    }

    override suspend fun persist(roomUser: RoomUser): RoomUser {
        return cassandraRoomUserRepository.save(
            CassandraRoomUserEntity.fromDomain(roomUser)
        ).toDomain()
    }
}