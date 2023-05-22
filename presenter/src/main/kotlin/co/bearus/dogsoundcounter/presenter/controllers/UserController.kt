package co.bearus.dogsoundcounter.presenter.controllers

import co.bearus.dogsoundcounter.presenter.dto.CreateNewUserRequest
import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.user.CreateNewUserUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByEmailUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val createNewUserUseCase: CreateNewUserUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
) {
    @PostMapping
    suspend fun createNewUser(
        @RequestBody request: CreateNewUserRequest,
    ) = withUseCase(
        useCase = createNewUserUseCase,
        param = CreateNewUserUseCase.Input(
            email = request.email,
            password = request.password,
        ),
        mappingFunction = UserResponse::from
    )

    @GetMapping(params = ["email"])
    suspend fun getUserByEmail(
        @RequestParam email: String,
    ) = withUseCase(
        useCase = getUserByEmailUseCase,
        param = GetUserByEmailUseCase.Input(
            email = email,
        ),
        mappingFunction = UserResponse::from
    )
}
