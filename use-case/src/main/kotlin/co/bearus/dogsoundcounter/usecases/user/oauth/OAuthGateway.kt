package co.bearus.dogsoundcounter.usecases.user.oauth

import co.bearus.dogsoundcounter.entities.OAuthResult

interface OAuthGateway {
    suspend fun authenticate(token: String): OAuthResult
}