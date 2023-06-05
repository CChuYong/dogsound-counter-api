package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CassandraUserRepository : CoroutineCrudRepository<CassandraUserEntity, String> {
    suspend fun findFirstByEmail(email: String): CassandraUserEntity?
    suspend fun getByUserId(userId: String): CassandraUserEntity
}
