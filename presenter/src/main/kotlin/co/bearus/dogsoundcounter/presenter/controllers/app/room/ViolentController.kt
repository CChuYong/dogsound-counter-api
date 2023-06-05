package co.bearus.dogsoundcounter.presenter.controllers.app.room

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.CreateNewViolentRequest
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.room.GetRoomByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.violent.CreateNewViolentUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/rooms/{roomId}/violents")
class ViolentController(
    private val getRoomById: GetRoomByIdUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val createNewViolent: CreateNewViolentUseCase,
) {
    @PostMapping
    suspend fun createViolent(
        @PathVariable roomId: String,
        @RequestUser user: LoginUser,
        @RequestBody dto: CreateNewViolentRequest,
    ) = withUseCase(
        useCase = createNewViolent,
        param = CreateNewViolentUseCase.Input(
            room = withUseCase(
                useCase = getRoomById,
                param = roomId,
            ),
            name = dto.name,
            description = dto.description,
            price = dto.price,
            createUser = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
        )
    )
}