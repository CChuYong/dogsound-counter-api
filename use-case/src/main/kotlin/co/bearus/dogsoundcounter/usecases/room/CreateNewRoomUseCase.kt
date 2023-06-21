package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.entities.RoomUser
import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.exception.room.RoomNameValidationException
import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateNewRoomUseCase(
    private val identityGenerator: IdentityGenerator,
    private val roomRepository: RoomRepository,
    private val roomUserRepository: RoomUserRepository,
) : UseCase<CreateNewRoomUseCase.Input, Room> {
    data class Input(
        val roomName: String,
        val owner: User,
        val roomImageUrl: String,
        val initialUsers: List<User>,
    )

    override suspend fun execute(input: Input): Room {
        val parsedRoomName = input.roomName.replace(" ", "")
        if (parsedRoomName.isBlank()) throw RoomNameValidationException()

        val newRoom = Room.newInstance(
            roomId = identityGenerator.createIdentity(),
            roomName = input.roomName,
            roomImageUrl = input.roomImageUrl,
            ownerId = input.owner.userId,
        )

        val roomUser = RoomUser.newInstance(
            roomUserId = identityGenerator.createIdentity(),
            roomId = newRoom.roomId,
            userId = input.owner.userId,
            nickname = input.owner.nickname,
            invitedBy = input.owner.userId,
        )
        roomUserRepository.persist(roomUser)

        input.initialUsers.forEach { initialUser ->
            val initialRoomUser = RoomUser.newInstance(
                roomUserId = identityGenerator.createIdentity(),
                roomId = newRoom.roomId,
                userId = initialUser.userId,
                nickname = initialUser.nickname,
                invitedBy = input.owner.userId,
            )
            roomUserRepository.persist(initialRoomUser)
        }

        return roomRepository.persist(newRoom)
    }
}