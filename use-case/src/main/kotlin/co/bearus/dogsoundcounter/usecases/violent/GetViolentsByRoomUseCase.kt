package co.bearus.dogsoundcounter.usecases.violent

import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.entities.Violent
import co.bearus.dogsoundcounter.usecases.UseCase

class GetViolentsByRoomUseCase(
    private val violentRepository: ViolentRepository,
) : UseCase<GetViolentsByRoomUseCase.Input, List<Violent>> {
    data class Input(
        val room: Room,
    )

    override suspend fun execute(input: GetViolentsByRoomUseCase.Input): List<Violent> {
        return violentRepository.findAllByRoomId(input.room.roomId)
    }
}
