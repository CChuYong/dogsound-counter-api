package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.RoomUserPrice
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomUserPriceEntity
import co.bearus.dogsoundcounter.usecases.room.RoomUserPriceRepository
import org.springframework.stereotype.Repository

@Repository
class RoomUserPriceRepositoryImpl(
    private val cassandraRoomUserPriceRepository: CassandraRoomUserPriceRepository,
): RoomUserPriceRepository {
    override suspend fun findByRoomUser(roomUserId: String, startDay: String): RoomUserPrice? {
        return cassandraRoomUserPriceRepository.findFirstByRoomUserIdAndStartDay(roomUserId, startDay)?.toDomain()
    }

    override suspend fun findAllByRoomUser(roomUserId: String): List<RoomUserPrice> {
        return cassandraRoomUserPriceRepository.findAllByRoomUserId(roomUserId).map { it.toDomain() }
    }

    override suspend fun findAllByUser(userId: String, startDay: String): List<RoomUserPrice> {
        return cassandraRoomUserPriceRepository.findAllByUserIdAndStartDay(userId, startDay).map { it.toDomain() }
    }

    override suspend fun sumByRoomUser(roomUserId: String): Long {
        return cassandraRoomUserPriceRepository.sumOfPriceByRoomUserId(roomUserId)
    }

    override suspend fun cumulateByRoomUser(roomUserId: String, userId: String, startDay: String, price: Int) {
        cassandraRoomUserPriceRepository.insertIfNotExists(roomUserId, startDay, userId)
        cassandraRoomUserPriceRepository.addPriceByRoomUserId(price, roomUserId, startDay)
    }

    override suspend fun persist(roomUserPrice: RoomUserPrice): RoomUserPrice {
        return cassandraRoomUserPriceRepository.save(CassandraRoomUserPriceEntity.fromDomain(roomUserPrice)).toDomain()
    }
}