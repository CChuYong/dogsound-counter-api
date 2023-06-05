package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.UserAlreadyExistsException
import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateNewUserUseCase(
    private val userRepository: UserRepository,
    private val identityGenerator: IdentityGenerator,
) : UseCase<CreateNewUserUseCase.Input, User> {
    data class Input(
        val email: String,
    )
    override suspend fun execute(input: Input): User {
        // #1. Check user with given email already exists
        val previousUser = userRepository.findUserByEmail(input.email)
        if (previousUser != null) throw UserAlreadyExistsException()
        val newUser = User.newInstance(
            userId = identityGenerator.createIdentity(),
            email = input.email,
        )
        return userRepository.persist(newUser)
    }
}