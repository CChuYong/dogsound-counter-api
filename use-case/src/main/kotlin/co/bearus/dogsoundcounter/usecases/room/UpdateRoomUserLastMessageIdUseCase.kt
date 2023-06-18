package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.RoomUser
import co.bearus.dogsoundcounter.usecases.UseCase

class UpdateRoomUserLastMessageIdUseCase(
    private val roomUserRepository: RoomUserRepository,
) : UseCase<UpdateRoomUserLastMessageIdUseCase.Input, RoomUser> {
    data class Input(
        val roomUser: RoomUser,
        val newLastIndex: String,
    )

    override suspend fun execute(input: Input): RoomUser {
        if (input.roomUser.lastReadMessageId == null || input.roomUser.lastReadMessageId!! < input.newLastIndex) {
            return roomUserRepository.persist(input.roomUser.copy(lastReadMessageId = input.newLastIndex))
        }
        return input.roomUser
    }


}
