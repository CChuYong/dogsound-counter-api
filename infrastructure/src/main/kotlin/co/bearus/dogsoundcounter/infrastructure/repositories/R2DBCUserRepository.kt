package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface R2DBCUserRepository: CoroutineCrudRepository<UserEntity, String> {
    suspend fun findByEmail(email: String): UserEntity?
}
