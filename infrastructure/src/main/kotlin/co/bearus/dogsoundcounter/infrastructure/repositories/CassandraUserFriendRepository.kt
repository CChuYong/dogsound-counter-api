package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserFriendEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraUserFriendRepository : CoroutineCrudRepository<CassandraUserFriendEntity, String>{
    suspend fun findAllByUserId1OrUserId2(userId1: String, userId2: String): List<CassandraUserFriendEntity>
}