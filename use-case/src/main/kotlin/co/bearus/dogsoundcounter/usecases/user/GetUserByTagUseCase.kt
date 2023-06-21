package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.user.UserNotFoundException
import co.bearus.dogsoundcounter.usecases.UseCase

class GetUserByTagUseCase(
    private val userRepository: UserRepository,
) : UseCase<String, User> {

    override suspend fun execute(input: String): User {
        val splitted = input.split("#")
        if (splitted.size < 2) throw UserNotFoundException()
        return userRepository.getByNicknameAndTag(splitted[0], splitted[1]) ?: throw UserNotFoundException()
    }
}
