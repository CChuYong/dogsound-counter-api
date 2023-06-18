package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.*
import co.bearus.dogsoundcounter.usecases.*
import co.bearus.dogsoundcounter.usecases.user.UserDeviceRepository
import kotlinx.coroutines.*

class CreateNewMessageUseCase(
    private val identityGenerator: IdentityGenerator,
    private val messageRepository: MessageRepository,
    private val notificationGateway: NotificationGateway,
    private val userDeviceRepository: UserDeviceRepository,
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

        val price = if(roomUserIds.size > 1)
            (violent?.violentPrice ?: 0) / (roomUserIds.size - 1)
        else 0 //혼자 있는 방이면 나쁜말 불가?
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
            input.roomUsers.map { it.userId }.forEach { id ->
                val device = userDeviceRepository.getUserDevices(id)
                val notification = Notification(
                    title = input.room.roomName,
                    body = input.content,
                )
                notificationGateway.sendMulti(device.map { it.fcmToken }, notification)
            }
        }


        return messageRepository.persist(newMessage)
    }

}
