package co.bearus.dogsoundcounter.presenter.controllers

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.CreateNewMessageRequest
import co.bearus.dogsoundcounter.presenter.dto.CreateNewRoomRequest
import co.bearus.dogsoundcounter.presenter.dto.CreateNewViolentRequest
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.message.CreateNewMessageUseCase
import co.bearus.dogsoundcounter.usecases.room.CreateNewRoomUseCase
import co.bearus.dogsoundcounter.usecases.room.GetRoomByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.violent.CreateNewViolentUseCase
import co.bearus.dogsoundcounter.usecases.violent.GetViolentByIdUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val createNewRoom: CreateNewRoomUseCase,
    private val getRoomById: GetRoomByIdUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val createNewMessage: CreateNewMessageUseCase,
    private val getViolentById: GetViolentByIdUseCase,
    private val createNewViolent: CreateNewViolentUseCase,
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

    @PostMapping("/{roomId}/messages")
    suspend fun createMessage(
        @PathVariable roomId: String,
        @RequestBody dto: CreateNewMessageRequest,
    ) = withUseCase(
        useCase = createNewMessage,
        param = CreateNewMessageUseCase.Input(
            room = withUseCase(
                useCase = getRoomById,
                param = roomId,
            ),
            violent = withUseCase(
                useCase = getViolentById,
                param = dto.violentId,
            ),
            speaker = withUseCase(
                useCase = getUserById,
                param = dto.speakerId,
            ),
            catcher = withUseCase(
                useCase = getUserById,
                param = dto.catcherId,
            ),
            content = dto.content,
        ),
    )

    @PostMapping("/{roomId}/violents")
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