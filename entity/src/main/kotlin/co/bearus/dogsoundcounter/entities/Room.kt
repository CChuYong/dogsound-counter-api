package co.bearus.dogsoundcounter.entities

/**
 * 나쁜말 방
 */
data class Room(
    val roomId: String,
    val roomName: String,
    val ownerId: String,
    val createdAtTs: Long,
)
