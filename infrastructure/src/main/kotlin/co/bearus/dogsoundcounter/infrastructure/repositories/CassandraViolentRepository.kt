package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraViolentEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraViolentRepository : CoroutineCrudRepository<CassandraViolentEntity, String> {
    suspend fun getByViolentId(violentId: String): CassandraViolentEntity
}
