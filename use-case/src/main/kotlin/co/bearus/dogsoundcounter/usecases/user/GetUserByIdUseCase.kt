package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.UseCase

class GetUserByIdUseCase(
    private val userRepository: UserRepository,
) : UseCase<String, User> {

    override suspend fun execute(input: String): User {
        return userRepository.getById(input)
    }
}
