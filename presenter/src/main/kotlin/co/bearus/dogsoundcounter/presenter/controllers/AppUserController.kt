package co.bearus.dogsoundcounter.presenter.controllers

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.AuthenticationRequest
import co.bearus.dogsoundcounter.presenter.dto.AuthenticationResultResponse
import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/users")
class AppUserController(
    private val authUserWithProvider: AuthUserWithProviderUseCase,
    private val getUserById: GetUserByIdUseCase,
) {
    @PostMapping("/oauth")
    suspend fun authenticateUser(
        @RequestBody request: AuthenticationRequest,
    ) = withUseCase(
        useCase = authUserWithProvider,
        param = request.to(),
        mappingFunction = AuthenticationResultResponse::from,
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