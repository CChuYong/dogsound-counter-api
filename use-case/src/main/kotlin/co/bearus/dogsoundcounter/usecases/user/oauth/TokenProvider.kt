package co.bearus.dogsoundcounter.usecases.user.oauth

import co.bearus.dogsoundcounter.entities.User

interface TokenProvider {
    suspend fun createAccessToken(user: User): String
    suspend fun createRefreshToken(user: User): String
}