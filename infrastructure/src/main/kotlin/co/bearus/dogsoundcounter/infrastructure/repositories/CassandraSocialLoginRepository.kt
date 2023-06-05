package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraSocialLoginUserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CassandraSocialLoginRepository : CoroutineCrudRepository<CassandraSocialLoginUserEntity, String> {
    suspend fun findFirstByProviderAndProviderKey(
        provider: String,
        providerKey: String,
    ): CassandraSocialLoginUserEntity?
}
