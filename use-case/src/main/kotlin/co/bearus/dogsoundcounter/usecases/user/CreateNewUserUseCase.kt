package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.UserAlreadyExistsException
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateNewUserUseCase(
    private val userRepository: UserRepository,
) : UseCase<CreateNewUserUseCase.Input, User> {
    data class Input(
        val email: String,
        val password: String,
    )

    override suspend fun execute(input: Input): User {
        // #1. Check user with given email already exists
        val previousUser = userRepository.findUserByEmail(input.email)
        if (previousUser != null) throw UserAlreadyExistsException()

        val newUser = User.newInstance(input.email, input.password)
        return userRepository.persist(newUser)
    }
}
