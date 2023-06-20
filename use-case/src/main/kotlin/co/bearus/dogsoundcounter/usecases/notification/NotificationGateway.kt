package co.bearus.dogsoundcounter.usecases.notification

interface NotificationGateway {
    fun sendSingle(fcmToken: String, notification: Notification)
    fun sendMulti(fcmTokens: List<String>, notification: Notification)
}
