package co.bearus.dogsoundcounter.usecases.violent

import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.entities.Violent
import co.bearus.dogsoundcounter.usecases.UseCase
import co.bearus.dogsoundcounter.usecases.message.GetMessagesByRoomUseCase

class GetViolentsByRoomUseCase(
    private val violentRepository: ViolentRepository,
): UseCase<GetMessagesByRoomUseCase.Input, List<Violent>> {
    data class Input(
        val room: Room,
    )

    override suspend fun execute(input: GetMessagesByRoomUseCase.Input): List<Violent> {
        return violentRepository.findAllByRoomId(input.room.roomId)
    }
}