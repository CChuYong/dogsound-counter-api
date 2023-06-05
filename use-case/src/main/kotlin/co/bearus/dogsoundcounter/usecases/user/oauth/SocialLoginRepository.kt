package co.bearus.dogsoundcounter.usecases.user.oauth

import co.bearus.dogsoundcounter.entities.SocialLoginUser
import co.bearus.dogsoundcounter.entities.UserProvider

interface SocialLoginRepository {
    suspend fun findByProvider(provider: UserProvider, providerKey: String): SocialLoginUser?
    suspend fun persist(user: SocialLoginUser): SocialLoginUser
}