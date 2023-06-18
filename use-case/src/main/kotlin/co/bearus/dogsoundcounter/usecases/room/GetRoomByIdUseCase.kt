package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.usecases.UseCase

class GetRoomByIdUseCase(
    private val roomRepository: RoomRepository,
) : UseCase<String, Room> {
    override suspend fun execute(input: String): Room {
        return roomRepository.getById(input)
    }
}
