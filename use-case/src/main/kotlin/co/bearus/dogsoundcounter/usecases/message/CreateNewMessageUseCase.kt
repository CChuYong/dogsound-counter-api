package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.*
import co.bearus.dogsoundcounter.usecases.*
import co.bearus.dogsoundcounter.usecases.notification.Notification
import co.bearus.dogsoundcounter.usecases.notification.NotificationGateway
import co.bearus.dogsoundcounter.usecases.room.RoomUserPriceRepository
import co.bearus.dogsoundcounter.usecases.user.UserDeviceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CreateNewMessageUseCase(
    private val identityGenerator: IdentityGenerator,
    private val messageRepository: MessageRepository,
    private val notificationGateway: NotificationGateway,
    private val userDeviceRepository: UserDeviceRepository,
    private val messagePublisherFactory: MessagePublisherFactory,
    private val roomUserPriceRepository: RoomUserPriceRepository,
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

        val price = if (roomUserIds.size > 1)
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
            val week = LocalizedWeek(Locale.KOREA)
            input.roomUsers.forEach {
                val id = it.userId
                if (violent != null && violent.violentPrice > 0) {
                    if (id != input.speaker.userId) {
                        roomUserPriceRepository.cumulateByRoomUser(
                            roomUserId = it.roomUserId,
                            price = price,
                            startDay = week.firstDay.toString(),
                        )
                    } else {
                        roomUserPriceRepository.cumulateByRoomUser(
                            roomUserId = it.roomUserId,
                            price = violent.violentPrice * -1,
                            startDay = week.firstDay.toString(),
                        )
                    }
                }


                val device = userDeviceRepository.getUserDevices(id)
                val publisher = messagePublisherFactory.getSuitableFactory(id)
                publisher.publishMessage(
                    id, ClientPacket(
                        packetType = PacketType.MESSAGE_RECEIVED,
                        payload = newMessage,
                    )
                )

                //send noti
                val notification = Notification(
                    title = input.room.roomName,
                    body = "${input.speaker.nickname}: ${input.content}",
                )
                notificationGateway.sendMulti(device.map { it.fcmToken }, notification)
            }
        }


        return messageRepository.persist(newMessage)
    }

}
