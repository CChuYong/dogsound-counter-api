package co.bearus.dogsoundcounter.presenter.dto

import co.bearus.dogsoundcounter.entities.UserProvider
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase

data class AuthenticationResultResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun from(dto: AuthUserWithProviderUseCase.Output) = AuthenticationResultResponse(
            accessToken = dto.accessToken,
            refreshToken = dto.refreshToken,
        )
    }
}

data class AuthenticationRequest(
    val provider: UserProvider,
    val token: String,
) {
    fun to() = AuthUserWithProviderUseCase.Input(
        provider = provider,
        token = token,
    )
}