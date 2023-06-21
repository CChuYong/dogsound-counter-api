package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.AuthenticationRequest
import co.bearus.dogsoundcounter.presenter.dto.AuthenticationResultResponse
import co.bearus.dogsoundcounter.presenter.dto.RefreshTokenRequest
import co.bearus.dogsoundcounter.presenter.dto.UpdateDeviceInfoRequest
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.user.CreateUserDeviceUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.RefreshUserWithTokenUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/app/auth")
class AppAuthController(
    private val authUserWithProvider: AuthUserWithProviderUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val refreshUserWithToken: RefreshUserWithTokenUseCase,
    private val createUserDevice: CreateUserDeviceUseCase,
    private val tokenProvider: TokenProvider,
) {
    @PostMapping
    suspend fun authenticateUser(
        @RequestBody request: AuthenticationRequest,
    ) = withUseCase(
        useCase = authUserWithProvider,
        param = request.to(),
        mappingFunction = AuthenticationResultResponse::from,
    )

    @PostMapping("/test")
    suspend fun testAuth() = withUseCase(
        useCase = getUserById,
        param = "test",
        mappingFunction = {
            AuthenticationResultResponse(
                accessToken = tokenProvider.createAccessToken(it),
                refreshToken = tokenProvider.createRefreshToken(it),
            )
        },
    )

    @PostMapping("/refresh")
    suspend fun refresh(
        @RequestBody dto: RefreshTokenRequest,
    ) = withUseCase(
        useCase = refreshUserWithToken,
        param = dto.to()
    )

    @PostMapping("/device")
    suspend fun registerDevice(
        @RequestUser user: LoginUser,
        @RequestBody dto: UpdateDeviceInfoRequest,
    ) = withUseCase(
        useCase = createUserDevice,
        param = CreateUserDeviceUseCase.Input(
            user = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            fcmToken = dto.fcmToken,
            deviceInfo = dto.deviceInfo,
        )
    )
}