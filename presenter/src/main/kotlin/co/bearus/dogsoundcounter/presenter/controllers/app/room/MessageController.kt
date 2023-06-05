package co.bearus.dogsoundcounter.presenter.controllers.app.room

import co.bearus.dogsoundcounter.presenter.dto.CreateNewMessageRequest
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.message.CreateNewMessageUseCase
import co.bearus.dogsoundcounter.usecases.message.GetMessagesByRoomUseCase
import co.bearus.dogsoundcounter.usecases.room.GetRoomByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.violent.GetViolentByIdUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/rooms/{roomId}/messages")
class MessageController(
    private val getRoomById: GetRoomByIdUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val createNewMessage: CreateNewMessageUseCase,
    private val getViolentById: GetViolentByIdUseCase,
    private val getMessagesByRoom: GetMessagesByRoomUseCase,
) {
    @PostMapping
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

    @GetMapping
    suspend fun getMessages(
        @PathVariable roomId: String,
    ) = withUseCase(
        useCase = getMessagesByRoom,
        param = GetMessagesByRoomUseCase.Input(
            room = withUseCase(
                useCase = getRoomById,
                param = roomId,
            ),
        )
    )
}