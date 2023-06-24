package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserFriendEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraUserFriendRepository : CoroutineCrudRepository<CassandraUserFriendEntity, String> {
    suspend fun findAllByUserId1(userId1: String): List<CassandraUserFriendEntity>
    suspend fun findAllByUserId2(userId2: String): List<CassandraUserFriendEntity>
    suspend fun findFirstByUserId1AndUserId2(userId1: String, userId2: String): CassandraUserFriendEntity?
}
