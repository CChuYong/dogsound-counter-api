package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.usecases.UseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider

class RefreshUserWithTokenUseCase(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository,
): UseCase<RefreshUserWithTokenUseCase.Input, RefreshUserWithTokenUseCase.Output> {

    data class Input(
        val refreshToken: String,
    )

    data class Output(
        val accessToken: String,
        val refreshToken: String,
    )

    override suspend fun execute(input: RefreshUserWithTokenUseCase.Input): Output {
        val userId = tokenProvider.extractUserIdFromToken(input.refreshToken) ?: throw RuntimeException()
        val user = userRepository.getById(userId)
        return Output(
            accessToken = tokenProvider.createAccessToken(user),
            refreshToken = tokenProvider.createRefreshToken(user),
        )
    }
}
