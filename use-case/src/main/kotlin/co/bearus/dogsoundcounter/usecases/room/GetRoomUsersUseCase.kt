package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.UseCase
import co.bearus.dogsoundcounter.usecases.user.UserRepository

class GetRoomUsersUseCase(
    private val roomUserRepository: RoomUserRepository,
    private val userRepository: UserRepository,
): UseCase<String, List<User>> {
    override suspend fun execute(input: String): List<User> {
        val users = roomUserRepository.findByRoomId(input)
        return users.map {
            userRepository.getById(it.userId)
        }
    }
}
