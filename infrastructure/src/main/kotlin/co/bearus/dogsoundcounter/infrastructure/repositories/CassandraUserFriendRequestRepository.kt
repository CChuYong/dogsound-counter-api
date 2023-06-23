package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserFriendRequestEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraUserFriendRequestRepository : CoroutineCrudRepository<CassandraUserFriendRequestEntity, String>{
    suspend fun findAllByFromUserId(fromUserId: String): List<CassandraUserFriendRequestEntity>
    suspend fun findAllByToUserId(toUserId: String): List<CassandraUserFriendRequestEntity>
    suspend fun findByFromUserIdAndToUserId(fromUserId: String, toUserId: String): CassandraUserFriendRequestEntity?
}