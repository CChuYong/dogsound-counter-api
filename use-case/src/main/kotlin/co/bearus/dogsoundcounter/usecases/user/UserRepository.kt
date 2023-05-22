package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User

interface UserRepository {
    suspend fun findUserByEmail(email: String): User?
    suspend fun persist(user: User): User
}
