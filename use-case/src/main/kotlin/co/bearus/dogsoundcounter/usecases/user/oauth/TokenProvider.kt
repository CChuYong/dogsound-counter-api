package co.bearus.dogsoundcounter.usecases.user.oauth

import co.bearus.dogsoundcounter.entities.User

interface TokenProvider {
    fun createAccessToken(user: User): String
    fun createRefreshToken(user: User): String
    fun extractUserIdFromToken(token: String): String
}