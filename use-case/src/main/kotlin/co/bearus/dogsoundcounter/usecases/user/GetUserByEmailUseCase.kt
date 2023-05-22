package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.UserNotFoundException
import co.bearus.dogsoundcounter.usecases.UseCase

class GetUserByEmailUseCase(
    private val userRepository: UserRepository,
): UseCase<GetUserByEmailUseCase.Input, User> {
    data class Input(
        val email: String
    )

    override suspend fun execute(input: Input): User {
        return userRepository.findUserByEmail(input.email) ?: throw UserNotFoundException()
    }
}
