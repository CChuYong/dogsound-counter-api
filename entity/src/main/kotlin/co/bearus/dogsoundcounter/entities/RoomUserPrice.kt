package co.bearus.dogsoundcounter.entities

data class RoomUserPrice(
    val roomUserId: String,
    val startDay: String,
    val userId: String,
    val cumulatedPrice: Int,
)
