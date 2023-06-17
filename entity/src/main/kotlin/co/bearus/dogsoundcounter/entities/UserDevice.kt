package co.bearus.dogsoundcounter.entities

data class UserDevice(
    val userId: String,
    val fcmToken: String,
    val deviceInfo: String,
    val createdAtTs: Long,
)
