package co.bearus.dogsoundcounter.presenter.controllers

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.CreateNewRoomRequest
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.room.CreateNewRoomUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val createNewRoom: CreateNewRoomUseCase,
) {
    @PostMapping
    suspend fun createNewRoom(
        @RequestUser user: LoginUser,
        @RequestBody body: CreateNewRoomRequest,
    ) = withUseCase(
        useCase = createNewRoom,
        param = CreateNewRoomUseCase.Input(
            roomName = body.roomName,
            ownerId = user.userId,
        ),
    )
}