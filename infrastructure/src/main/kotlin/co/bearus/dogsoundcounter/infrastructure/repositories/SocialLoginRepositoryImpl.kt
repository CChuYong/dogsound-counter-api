package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.SocialLoginUser
import co.bearus.dogsoundcounter.entities.UserProvider
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraSocialLoginUserEntity
import co.bearus.dogsoundcounter.usecases.user.oauth.SocialLoginRepository
import org.springframework.stereotype.Repository

@Repository
class SocialLoginRepositoryImpl(
    private val cassandraSocialLoginRepository: CassandraSocialLoginRepository,
): SocialLoginRepository {
    override suspend fun findByProvider(provider: UserProvider, providerKey: String): SocialLoginUser? {
        return cassandraSocialLoginRepository.findFirstByProviderAndProviderKey(
            provider = provider.name,
            providerKey = providerKey,
        )?.toDomain()
    }

    override suspend fun persist(user: SocialLoginUser): SocialLoginUser {
        return cassandraSocialLoginRepository.save(
            CassandraSocialLoginUserEntity.fromDomain(user)
        ).toDomain()
    }
}