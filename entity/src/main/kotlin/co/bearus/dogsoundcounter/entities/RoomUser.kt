package co.bearus.dogsoundcounter.entities

data class RoomUser(
    val roomUserId: String,
    val roomId: String,
    val userId: String,
    val nickname: String,
    val invitedBy: String,
    val createdAtTs: Long,
) {
    companion object {
        fun newInstance(
            roomUserId: String,
            roomId: String,
            userId: String,
            nickname: String,
            invitedBy: String,
        ) = RoomUser(
            roomUserId = roomUserId,
            roomId = roomId,
            userId = userId,
            nickname = nickname,
            invitedBy = invitedBy,
            createdAtTs = System.currentTimeMillis(),
        )
    }
}
