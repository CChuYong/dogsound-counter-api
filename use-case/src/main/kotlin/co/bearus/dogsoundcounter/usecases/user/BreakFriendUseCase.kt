package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.FriendRequestNotValidException
import co.bearus.dogsoundcounter.usecases.UseCase

class BreakFriendUseCase(
    private val friendRepository: FriendRepository,
) : UseCase<BreakFriendUseCase.Input, BreakFriendUseCase.Output> {
    data class Input(
        val me: User,
        val target: User,
    )

    data class Output(
        val user1: String,
        val user2: String,
    )

    override suspend fun execute(input: Input): Output {
        val result = friendRepository.breakFriend(input.me.userId, input.target.userId)
        if (!result) throw FriendRequestNotValidException()
        return Output(
            user1 = input.me.userId,
            user2 = input.target.userId,
        )
    }
}
