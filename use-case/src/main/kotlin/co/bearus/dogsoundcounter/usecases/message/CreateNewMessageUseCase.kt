package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.*
import co.bearus.dogsoundcounter.usecases.*
import kotlinx.coroutines.*

class CreateNewMessageUseCase(
    private val identityGenerator: IdentityGenerator,
    private val messageRepository: MessageRepository,
    private val messagePublisherFactory: MessagePublisherFactory,
    private val objectSerializer: ObjectSerializer,
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

        val price = (violent?.violentPrice ?: 0) / (roomUserIds.size - 1)
        val newMessage = Message.newInstance(
            messageId = identityGenerator.createIdentity(),
            roomId = input.room.roomId,
            violentId = violent?.violentId ?: "",
            violentPrice = price,
            speakerId = input.speaker.userId,
            catcherId = input.catcher.userId,
            content = input.content,
        )
        CoroutineScope(Dispatchers.IO).launch {
            roomUserIds.forEach { id ->
                val publisher = messagePublisherFactory.getSuitableFactory(id)
                publisher.publishMessage(
                    id,
                    ClientPacket(
                        packetType = PacketType.MESSAGE_RECEIVED,
                        payload = objectSerializer.serialize(newMessage)
                    )
                ).subscribe()
            }
        }


        return messageRepository.persist(newMessage)
    }

}
