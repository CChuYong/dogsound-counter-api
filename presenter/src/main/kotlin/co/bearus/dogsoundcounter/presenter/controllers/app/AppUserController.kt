package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.*
import co.bearus.dogsoundcounter.presenter.parallelMap
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.LocalizedWeek
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
import co.bearus.dogsoundcounter.usecases.room.RoomRepository
import co.bearus.dogsoundcounter.usecases.room.RoomUserPriceRepository
import co.bearus.dogsoundcounter.usecases.user.*
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.springframework.web.bind.annotation.*
import java.time.ZoneOffset
import java.util.*

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
