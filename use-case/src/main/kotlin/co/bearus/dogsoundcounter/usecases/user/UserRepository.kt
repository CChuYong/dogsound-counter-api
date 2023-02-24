package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import reactor.core.publisher.Mono

interface UserRepository {
    fun findUserByIdentifierOrNull(id: String): Mono<User>
    fun persist(user: User): Mono<User>
}