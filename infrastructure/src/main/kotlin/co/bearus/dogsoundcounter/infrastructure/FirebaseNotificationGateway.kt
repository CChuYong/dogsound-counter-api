package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.usecases.notification.Notification
import co.bearus.dogsoundcounter.usecases.notification.NotificationGateway
import com.google.firebase.messaging.*
import org.springframework.stereotype.Component

@Component
class FirebaseNotificationGateway(
    private val firebaseMessaging: FirebaseMessaging,
) : NotificationGateway {
    override fun sendSingle(fcmToken: String, notification: Notification) {
        val message: Message = Message.builder()
            .setNotification(
                com.google.firebase.messaging.Notification.builder().setTitle(notification.title)
                    .setBody(notification.body).build()
            )
            .setToken(fcmToken)
            .setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setSound("default").build()).build())
            .build()
        try {
            firebaseMessaging.sendAsync(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun sendMulti(fcmTokens: List<String>, notification: Notification) {
        if (fcmTokens.isEmpty()) return
        val message: MulticastMessage = MulticastMessage.builder()
            .setNotification(
                com.google.firebase.messaging.Notification.builder().setTitle(notification.title)
                    .setBody(notification.body).build()
            )
            .addAllTokens(fcmTokens)
            .setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setSound("default").build()).build())
            .build()
        try {
            firebaseMessaging.sendMulticastAsync(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
