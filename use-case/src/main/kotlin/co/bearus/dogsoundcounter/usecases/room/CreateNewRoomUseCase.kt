package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateNewRoomUseCase(
    private val identityGenerator: IdentityGenerator,
    private val roomRepository: RoomRepository,
) : UseCase<CreateNewRoomUseCase.Input, Room> {
    data class Input(
        val roomName: String,
        val owner: User,
    )

    override suspend fun execute(input: Input): Room {
        val newRoom = Room.newInstance(
            roomId = identityGenerator.createIdentity(),
            roomName = input.roomName,
            ownerId = input.owner.userId,
        )
        return roomRepository.persist(newRoom)
    }
}