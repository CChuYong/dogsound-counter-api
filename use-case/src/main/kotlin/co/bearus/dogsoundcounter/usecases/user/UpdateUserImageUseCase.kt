package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.UseCase

class UpdateUserImageUseCase(
    private val userRepository: UserRepository,
) : UseCase<UpdateUserImageUseCase.Input, User> {
    data class Input(
        val user: User,
        val imageUrl: String,
    )

    override suspend fun execute(input: Input): User {
        val newUser =
            input.user.copy(profileImgUrl = input.imageUrl)
        return userRepository.persist(newUser)
    }
}