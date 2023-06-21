package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.RoomUserPrice
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomUserPriceEntity
import co.bearus.dogsoundcounter.usecases.room.RoomUserPriceRepository
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate
import org.springframework.stereotype.Repository

@Repository
class RoomUserPriceRepositoryImpl(
    private val cassandraRoomUserPriceRepository: CassandraRoomUserPriceRepository,
    private val reactiveCassandraTemplate: ReactiveCassandraTemplate,
): RoomUserPriceRepository {
    override suspend fun findByRoomUser(roomUserId: String, startDay: String): RoomUserPrice? {
        return cassandraRoomUserPriceRepository.findFirstByRoomUserIdAndStartDay(roomUserId, startDay)?.toDomain()
    }

    override suspend fun findAllByRoomUser(roomUserId: String): List<RoomUserPrice> {
        return cassandraRoomUserPriceRepository.findAllByRoomUserId(roomUserId).map { it.toDomain() }
    }

    override suspend fun findAllByUser(userId: String, startDay: String): List<RoomUserPrice> {
        return cassandraRoomUserPriceRepository.findAllByRoomUserIdAndStartDay(userId, startDay).map { it.toDomain() }
    }

    override suspend fun sumByRoomUser(roomUserId: String): Long {
        return cassandraRoomUserPriceRepository.sumOfPriceByRoomUserId(roomUserId)
    }

    override suspend fun cumulateByRoomUser(roomUserId: String, startDay: String, price: Int) {
        reactiveCassandraTemplate.execute(
            SimpleStatement.newInstance(
                "UPDATE room_user_price SET cumulated_price = cumulated_price + $price WHERE room_user_id = '${roomUserId}' AND start_day = '${startDay}'",
            )
        ).awaitSingleOrNull()
    }

    override suspend fun persist(roomUserPrice: RoomUserPrice): RoomUserPrice {
        return cassandraRoomUserPriceRepository.save(CassandraRoomUserPriceEntity.fromDomain(roomUserPrice)).toDomain()
    }
}