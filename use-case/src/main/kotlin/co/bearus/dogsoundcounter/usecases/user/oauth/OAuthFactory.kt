package co.bearus.dogsoundcounter.usecases.user.oauth

import co.bearus.dogsoundcounter.entities.UserProvider

interface OAuthFactory {
    fun of(userProvider: UserProvider): OAuthGateway
}