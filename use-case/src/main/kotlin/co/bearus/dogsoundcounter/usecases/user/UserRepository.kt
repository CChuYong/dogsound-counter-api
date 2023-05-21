package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import reactor.core.publisher.Mono

interface UserRepository {
    suspend fun findUserByEmail(id: String): User?
    suspend fun persist(user: User): User
}
