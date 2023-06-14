package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.Message
import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.usecases.UseCase

class GetMessagesByRoomUseCase(
    private val messageRepository: MessageRepository,
): UseCase<GetMessagesByRoomUseCase.Input, List<Message>> {
    data class Input(
        val room: Room,
        val queryMode: QueryMode? = null,
    )

    data class QueryMode(
        val baseMessageId: String,
        val fetchForward: Boolean,
    )

    override suspend fun execute(input: Input): List<Message> {
        return if (input.queryMode == null) messageRepository.findMessageByRoomId(input.room.roomId)
        else {
            if(input.queryMode.fetchForward) messageRepository.findMessageByRoomIdAfter(
                input.room.roomId,
                input.queryMode.baseMessageId,
                20
            )
            else messageRepository.findMessageByRoomIdBefore(
                input.room.roomId,
                input.queryMode.baseMessageId,
                20
            )
        }
    }
}