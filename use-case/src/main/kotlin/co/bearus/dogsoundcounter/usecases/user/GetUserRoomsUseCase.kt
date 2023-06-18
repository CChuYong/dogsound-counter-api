package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.RoomUser
import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.UseCase
import co.bearus.dogsoundcounter.usecases.room.RoomRepository
import co.bearus.dogsoundcounter.usecases.room.RoomUserRepository

class GetUserRoomsUseCase(
    private val roomUserRepository: RoomUserRepository,
    private val roomRepository: RoomRepository,
) : UseCase<GetUserRoomsUseCase.Input, GetUserRoomsUseCase.Output> {
    data class Input(
        val user: User,
    )

    data class Output(
        val list: List<RoomUser>
    )

    override suspend fun execute(input: Input): Output {
        return Output(
            roomUserRepository.findByUserId(input.user.userId)
        )
    }
}
