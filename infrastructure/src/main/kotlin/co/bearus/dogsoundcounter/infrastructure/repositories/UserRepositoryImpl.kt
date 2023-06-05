package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraUserEntity
import co.bearus.dogsoundcounter.usecases.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val cassandraUserRepository: CassandraUserRepository,
) : UserRepository {
    override suspend fun getById(id: String): User {
        return cassandraUserRepository.getByUserId(id).toDomain()
    }

    override suspend fun findUserByEmail(email: String): User? {
        return cassandraUserRepository.findFirstByEmail(email)?.toDomain()
    }

    override suspend fun persist(user: User): User {
        return cassandraUserRepository.save(
            CassandraUserEntity.fromDomain(user)
        ).toDomain()
    }
}
