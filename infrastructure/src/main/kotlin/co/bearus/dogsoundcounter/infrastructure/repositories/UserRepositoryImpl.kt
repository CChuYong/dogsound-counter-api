package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.infrastructure.entities.UserEntity
import co.bearus.dogsoundcounter.usecases.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val r2DBCUserRepository: R2DBCUserRepository,
): UserRepository {
    override suspend fun findUserByEmail(email: String): User? {
        return r2DBCUserRepository.findByEmail(email)?.toDomain()
    }

    override suspend fun persist(user: User): User {
        return r2DBCUserRepository.save(UserEntity.fromDomain(user)).toDomain()
    }
}
