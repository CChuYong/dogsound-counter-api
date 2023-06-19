package co.bearus.dogsoundcounter.presenter.controllers.app.room

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.CreateNewRoomRequest
import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.room.CreateNewRoomUseCase
import co.bearus.dogsoundcounter.usecases.room.GetRoomUsersUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/app/rooms")
class RoomController(
    private val createNewRoom: CreateNewRoomUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val getRoomUsers: GetRoomUsersUseCase,
) {
    @PostMapping
    suspend fun createNewRoom(
        @RequestUser user: LoginUser,
        @RequestBody body: CreateNewRoomRequest,
    ) = withUseCase(
        useCase = createNewRoom,
        param = CreateNewRoomUseCase.Input(
            roomName = body.roomName,
            owner = withUseCase(
                useCase = getUserById,
                param = user.userId,
            )
        ),
    )

    @GetMapping("/{roomId}/users")
    suspend fun getUsers(
        @PathVariable roomId: String,
    ) = withUseCase(
        useCase = getRoomUsers,
        param = roomId,
        mappingFunction = { it.map(UserResponse::from) },
    )
}
