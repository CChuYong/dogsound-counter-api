package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserEntity
import org.springframework.data.cassandra.repository.AllowFiltering
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraUserRepository : CoroutineCrudRepository<CassandraUserEntity, String> {
    suspend fun findFirstByEmail(email: String): CassandraUserEntity?

    @AllowFiltering
    suspend fun findFirstByNicknameAndTag(nickname: String, tag: String): CassandraUserEntity?
    suspend fun getByUserId(userId: String): CassandraUserEntity
}
