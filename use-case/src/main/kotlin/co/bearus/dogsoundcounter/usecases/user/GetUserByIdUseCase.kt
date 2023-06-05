package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.UseCase

class GetUserByIdUseCase(
    private val userRepository: UserRepository,
): UseCase<GetUserByIdUseCase.Input, User> {
    data class Input(
        val userId: String
    )

    override suspend fun execute(input: Input): User {
        return userRepository.getById(input.userId)
    }
}
