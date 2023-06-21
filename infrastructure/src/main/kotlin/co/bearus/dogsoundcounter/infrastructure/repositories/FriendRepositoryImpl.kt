package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserFriendEntity
import co.bearus.dogsoundcounter.usecases.user.FriendRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class FriendRepositoryImpl(
    private val cassandraUserFriendRepository: CassandraUserFriendRepository,
): FriendRepository {
    override suspend fun getFriends(userId: String): List<String> {
        val friends =
            cassandraUserFriendRepository.findAllByUserId1(userId) + cassandraUserFriendRepository.findAllByUserId2(
                userId
            )
        return friends.map {
            if (it.userId1 == userId) {
                it.userId2
            } else {
                it.userId1
            }
        }
    }

    override suspend fun makeFriend(userId1: String, userId2: String) {
        val entity = CassandraUserFriendEntity(
            userId1 = userId1,
            userId2 = userId2,
            createdAtTs = Timestamp(System.currentTimeMillis())
        )
        cassandraUserFriendRepository.save(entity)
    }
}