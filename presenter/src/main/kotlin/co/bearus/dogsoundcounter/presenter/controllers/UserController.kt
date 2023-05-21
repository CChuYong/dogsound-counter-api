package co.bearus.dogsoundcounter.presenter.controllers

import co.bearus.dogsoundcounter.presenter.dto.CreateNewUserRequest
import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.usecases.user.CreateNewUserUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val createNewUserUseCase: CreateNewUserUseCase,
) {
    @PostMapping
    suspend fun createNewUser(
        @RequestBody request: CreateNewUserRequest,
    ): UserResponse {
        return createNewUserUseCase
            .execute(
                CreateNewUserUseCase.Input(
                    email = request.email,
                    password = request.password,
                )
            ).let(UserResponse::from)
    }
}
