package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.UseCase

class UpdateNicknameUseCase(
    private val userRepository: UserRepository,
) : UseCase<UpdateNicknameUseCase.Input, User> {
    data class Input(
        val user: User,
        val newNickName: String,
    )

    override suspend fun execute(input: Input): User {
        val newUser = input.user.copy(nickname = input.newNickName)
        return userRepository.persist(newUser)
    }
}
