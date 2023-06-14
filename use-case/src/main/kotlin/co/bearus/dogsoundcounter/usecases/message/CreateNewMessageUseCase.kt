package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.*
import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateNewMessageUseCase(
    private val identityGenerator: IdentityGenerator,
    private val messageRepository: MessageRepository,
) : UseCase<CreateNewMessageUseCase.Input, Message> {
    data class Input(
        val room: Room,
        val violents: List<Violent>,
        val roomUsers: List<RoomUser>,
        val speaker: User,
        val catcher: User,
        val content: String,
    )

    override suspend fun execute(input: Input): Message {
        val violent = input.violents.firstOrNull() { input.content.contains(it.name) }
        val roomUserIds = input.roomUsers.map { it.roomUserId }.toMutableSet()
        roomUserIds.remove(input.speaker.userId)

        val price = (violent?.violentPrice ?: 0) / roomUserIds.size
        val newMessage = Message.newInstance(
            messageId = identityGenerator.createIdentity(),
            roomId = input.room.roomId,
            violentId = violent?.violentId ?: "",
            violentPrice = price,
            speakerId = input.speaker.userId,
            catcherId = input.catcher.userId,
            content = input.content,
        )
        return messageRepository.persist(newMessage)
    }

}