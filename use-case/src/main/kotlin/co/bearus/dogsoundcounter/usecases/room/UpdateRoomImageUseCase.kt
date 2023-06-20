package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.usecases.UseCase

class UpdateRoomImageUseCase(
    private val roomRepository: RoomRepository,
) : UseCase<UpdateRoomImageUseCase.Input, Room> {
    data class Input(
        val room: Room,
        val imageUrl: String,
    )

    override suspend fun execute(input: Input): Room {
        val newRoom =
            input.room.copy(roomImageUrl = input.imageUrl)
        return roomRepository.persist(newRoom)
    }
}