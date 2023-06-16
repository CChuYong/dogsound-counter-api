package co.bearus.dogsoundcounter.presenter.controllers.app.room

import co.bearus.dogsoundcounter.presenter.dto.CreateNewMessageRequest
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.message.CreateNewMessageUseCase
import co.bearus.dogsoundcounter.usecases.message.GetMessagesByRoomUseCase
import co.bearus.dogsoundcounter.usecases.room.GetRoomByIdUseCase
import co.bearus.dogsoundcounter.usecases.room.RoomUserRepository
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.violent.GetViolentByIdUseCase
import co.bearus.dogsoundcounter.usecases.violent.GetViolentsByRoomUseCase
import co.bearus.dogsoundcounter.usecases.violent.ViolentRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/rooms/{roomId}/messages")
class MessageController(
    private val getRoomById: GetRoomByIdUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val createNewMessage: CreateNewMessageUseCase,
    private val getMessagesByRoom: GetMessagesByRoomUseCase,
    private val roomUserRepository: RoomUserRepository,
    private val violentRepository: ViolentRepository,
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
            violents = violentRepository.findAllByRoomId(roomId),
            speaker = withUseCase(
                useCase = getUserById,
                param = dto.speakerId,
            ),
            catcher = withUseCase(
                useCase = getUserById,
                param = dto.catcherId,
            ),
            roomUsers = roomUserRepository.findByRoomId(roomId),
            content = dto.content,
        ),
    )

    @GetMapping
    suspend fun getMessages(
        @PathVariable roomId: String,
        @RequestParam(required = false) limit: Int?,
    ) = withUseCase(
        useCase = getMessagesByRoom,
        param = GetMessagesByRoomUseCase.Input(
            room = withUseCase(
                useCase = getRoomById,
                param = roomId,
            ),
            limit = limit ?: 100,
        )
    )

    @GetMapping(params = ["fetchType=FORWARD", "baseId"])
    suspend fun getMessagesAfter(
        @PathVariable roomId: String,
        @RequestParam baseId: String,
        @RequestParam(required = false) limit: Int?,
    ) = withUseCase(
        useCase = getMessagesByRoom,
        param = GetMessagesByRoomUseCase.Input(
            room = withUseCase(
                useCase = getRoomById,
                param = roomId,
            ),
            queryMode = GetMessagesByRoomUseCase.QueryMode(
                baseMessageId = baseId,
                fetchForward = true,
            ),
            limit = limit ?: 100,
        )
    )

    @GetMapping(params = ["fetchType=BACKWARD", "baseId"])
    suspend fun getMessagesBefore(
        @PathVariable roomId: String,
        @RequestParam baseId: String,
        @RequestParam(required = false) limit: Int?,
    ) = withUseCase(
        useCase = getMessagesByRoom,
        param = GetMessagesByRoomUseCase.Input(
            room = withUseCase(
                useCase = getRoomById,
                param = roomId,
            ),
            queryMode = GetMessagesByRoomUseCase.QueryMode(
                baseMessageId = baseId,
                fetchForward = false,
            ),
            limit = limit ?: 100,
        )
    )
}
