package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.MessagePublisher
import co.bearus.dogsoundcounter.usecases.user.UserDeviceRepository
import com.google.firebase.messaging.*
import org.springframework.stereotype.Component


@Component
class FirebaseMessagePublisher(
    private val userDeviceRepository: UserDeviceRepository,
): MessagePublisher {
    override suspend fun publishMessage(userId: String, message: ClientPacket): Boolean {
        val devices = userDeviceRepository.getUserDevices(userId)
        devices.forEach {
            sendNotification(it.fcmToken, "test1", "test2")
        }
        return true
    }

    fun sendNotification(fcmToken: String, title: String, description: String): String? {
        println("FWD ${fcmToken} -> $title")
        val message: Message = Message.builder()
            .setNotification(Notification.builder().setTitle(title).setBody(description).build())
            .setToken(fcmToken)
            .setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setSound("default").build()).build())
            .build()
        return try {
            FirebaseMessaging.getInstance().send(message)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}
