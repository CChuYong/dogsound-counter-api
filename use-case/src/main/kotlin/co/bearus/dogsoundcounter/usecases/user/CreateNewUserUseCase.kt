package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.UserAlreadyExistsException
import co.bearus.dogsoundcounter.entities.exception.user.UserNotFoundException
import co.bearus.dogsoundcounter.entities.utils.throwIfNotNull
import co.bearus.dogsoundcounter.usecases.UseCase
import kotlinx.coroutines.reactor.awaitSingleOrNull

class CreateNewUserUseCase(
    private val userRepository: UserRepository,
) : UseCase<CreateNewUserUseCase.Input, User> {
    data class Input(
        val identifier: String,
    )

    override suspend fun execute(input: Input): User {
        val previousUser = userRepository
            .findUserByIdentifierOrNull(input.identifier)
            .awaitSingleOrNull()
            .throwIfNotNull(UserAlreadyExistsException())

        val newUser = User.newInstance("", "")
        return userRepository.persist(newUser).awaitSingleOrNull() ?: throw UserNotFoundException()
    }
}