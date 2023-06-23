package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserFriendEntity
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserFriendRequestEntity
import co.bearus.dogsoundcounter.usecases.user.FriendRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class FriendRepositoryImpl(
    private val cassandraUserFriendRepository: CassandraUserFriendRepository,
    private val cassandraUserFriendRequestRepository: CassandraUserFriendRequestRepository,
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

    override suspend fun isFriend(userId1: String, userId2: String): Boolean {
        val friend = cassandraUserFriendRepository.findFirstByUserId1AndUserId2(userId1, userId2) ?:
            cassandraUserFriendRepository.findFirstByUserId1AndUserId2(userId2, userId1) ?: return false
        return true
    }

    override suspend fun getReceivedRequestIds(userId: String): List<String> {
        return cassandraUserFriendRequestRepository.findAllByToUserId(userId).map { it.fromUserId }
    }

    override suspend fun getSentRequestIds(userId: String): List<String> {
        return cassandraUserFriendRequestRepository.findAllByFromUserId(userId).map { it.toUserId }
    }

    override suspend fun sendRequest(fromId: String, toId: String) {
        val entity = CassandraUserFriendRequestEntity(
            fromUserId = fromId,
            toUserId = toId,
            createdAtTs = Timestamp(System.currentTimeMillis())
        )
        cassandraUserFriendRequestRepository.save(entity)
    }

    override suspend fun clearRequest(fromId: String, toId: String): Boolean {
        val entity = cassandraUserFriendRequestRepository.findByFromUserIdAndToUserId(fromId, toId) ?: return false
        cassandraUserFriendRequestRepository.delete(entity)
        return true
    }
}