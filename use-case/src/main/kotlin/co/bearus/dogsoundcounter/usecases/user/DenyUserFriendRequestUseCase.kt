package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.AlreadyFriendException
import co.bearus.dogsoundcounter.entities.exception.user.FriendRequestNotValidException
import co.bearus.dogsoundcounter.usecases.UseCase

class DenyUserFriendRequestUseCase(
    private val friendRepository: FriendRepository,
) : UseCase<DenyUserFriendRequestUseCase.Input, DenyUserFriendRequestUseCase.Output> {
    data class Input(
        val from: User,
        val target: User,
    )

    data class Output(
        val user1: User,
        val user2: User,
    )

    override suspend fun execute(input: Input): Output {
        if (!friendRepository.clearRequest(input.target.userId, input.from.userId)) throw FriendRequestNotValidException()
        return Output(
            user1 = input.from,
            user2 = input.target,
        )
    }
}