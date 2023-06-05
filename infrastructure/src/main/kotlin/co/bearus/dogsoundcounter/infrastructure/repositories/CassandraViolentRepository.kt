package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraViolentEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CassandraViolentRepository : CoroutineCrudRepository<CassandraViolentEntity, String> {
    suspend fun getByViolentId(violentId: String): CassandraViolentEntity
}
