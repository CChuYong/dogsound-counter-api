package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User

interface UserRepository {
    suspend fun getById(id: String): User
    suspend fun getByNicknameAndTag(nickname: String, tag: String): User?
    suspend fun findUserByEmail(email: String): User?
    suspend fun persist(user: User): User
}
