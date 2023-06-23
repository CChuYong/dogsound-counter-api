package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.AlreadyFriendException
import co.bearus.dogsoundcounter.entities.exception.user.FriendCannotBeMyselfException
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateNewFriendUseCase(
    private val friendRepository: FriendRepository,
) : UseCase<CreateNewFriendUseCase.Input, CreateNewFriendUseCase.Output> {
    data class Input(
        val user1: User,
        val user2: User,
    )

    data class Output(
        val user1: User,
        val user2: User,
    )

    override suspend fun execute(input: Input): Output {
        if (input.user1.userId == input.user2.userId) throw FriendCannotBeMyselfException()
        if (friendRepository.isFriend(input.user1.userId, input.user2.userId)) throw AlreadyFriendException()

        friendRepository.sendRequest(input.user1.userId, input.user2.userId)
        //sOME ALERT

        return Output(
            user1 = input.user1,
            user2 = input.user2,
        )
    }
}