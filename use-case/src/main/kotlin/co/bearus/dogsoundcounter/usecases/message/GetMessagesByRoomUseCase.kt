package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.Message
import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.usecases.UseCase

class GetMessagesByRoomUseCase(
    private val messageRepository: MessageRepository,
): UseCase<GetMessagesByRoomUseCase.Input, List<Message>> {
    data class Input(
        val room: Room,
    )

    override suspend fun execute(input: Input): List<Message> {
        return messageRepository.findMessageByRoomId(input.room.roomId)
    }
}