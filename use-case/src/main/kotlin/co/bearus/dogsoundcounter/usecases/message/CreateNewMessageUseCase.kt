package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.Message
import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.Violent
import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateNewMessageUseCase(
    private val identityGenerator: IdentityGenerator,
    private val messageRepository: MessageRepository,
) : UseCase<CreateNewMessageUseCase.Input, Message> {
    data class Input(
        val room: Room,
        val violent: Violent,
        val speaker: User,
        val catcher: User,
        val content: String,
    )

    override suspend fun execute(input: Input): Message {
        val newMessage = Message.newInstance(
            messageId = identityGenerator.createIdentity(),
            roomId = input.room.roomId,
            violentId = input.violent.violentId,
            violentPrice = input.violent.violentPrice,
            speakerId = input.speaker.userId,
            catcherId = input.catcher.userId,
            content = input.content,
        )
        return messageRepository.persist(newMessage)
    }

}