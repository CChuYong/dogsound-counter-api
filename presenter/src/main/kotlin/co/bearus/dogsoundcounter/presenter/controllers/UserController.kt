package co.bearus.dogsoundcounter.presenter.controllers

import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByEmailUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
) {
    @GetMapping(params = ["email"])
    suspend fun getUserByEmail(
        @RequestParam email: String,
    ) = withUseCase(
        useCase = getUserByEmailUseCase,
        param = GetUserByEmailUseCase.Input(
            email = email,
        ),
        mappingFunction = UserResponse::from,
    )
}
