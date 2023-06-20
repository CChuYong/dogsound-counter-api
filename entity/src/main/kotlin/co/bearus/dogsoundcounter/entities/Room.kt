package co.bearus.dogsoundcounter.entities

/**
 * 나쁜말 방
 */
data class Room(
    val roomId: String,
    val roomName: String,
    val ownerId: String,
    val roomImageUrl: String,
    val createdAtTs: Long,
) {
    companion object {
        fun newInstance(
            roomId: String,
            roomName: String,
            ownerId: String,
            roomImageUrl: String,
        ): Room {
            return Room(
                roomId = roomId,
                roomName = roomName,
                ownerId = ownerId,
                roomImageUrl = roomImageUrl,
                createdAtTs = System.currentTimeMillis(),
            )
        }
    }
}
