package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByTagUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/users")
class AppUserController(
    private val getUserById: GetUserByIdUseCase,
    private val getUserByTag: GetUserByTagUseCase,
) {
    @GetMapping("/{userId}")
    suspend fun getUser(
        @PathVariable userId: String,
    ) = withUseCase(
        useCase = getUserById,
        param = userId,
        mappingFunction = UserResponse::from,
    )

    @GetMapping(params = ["tag"])
    suspend fun getUserByTag(
        @RequestParam tag: String
    ) = withUseCase(
        useCase = getUserByTag,
        param = tag,
        mappingFunction = UserResponse::from,
    )
}
