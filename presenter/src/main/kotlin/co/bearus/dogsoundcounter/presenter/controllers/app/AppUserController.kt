package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.AuthenticationRequest
import co.bearus.dogsoundcounter.presenter.dto.AuthenticationResultResponse
import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/users")
class AppUserController(
    private val authUserWithProvider: AuthUserWithProviderUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val tokenProvider: TokenProvider,
) {
    @PostMapping("/oauth")
    suspend fun authenticateUser(
        @RequestBody request: AuthenticationRequest,
    ) = withUseCase(
        useCase = authUserWithProvider,
        param = request.to(),
        mappingFunction = AuthenticationResultResponse::from,
    )

    @PostMapping("/oauth/test")
    suspend fun testAuth() = withUseCase(
        useCase = getUserById,
        param = "01H256SJWQXX11JYFNQJS2RTEZ",
        mappingFunction = {
            AuthenticationResultResponse(
                accessToken = tokenProvider.createAccessToken(it),
                refreshToken = tokenProvider.createRefreshToken(it),
            )
        },
    )

    @GetMapping("/me")
    suspend fun getMe(
        @RequestUser user: LoginUser,
    ) = withUseCase(
        useCase = getUserById,
        param = user.userId,
        mappingFunction = UserResponse::from,
    )
}