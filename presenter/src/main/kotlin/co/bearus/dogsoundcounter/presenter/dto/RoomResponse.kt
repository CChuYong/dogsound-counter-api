package co.bearus.dogsoundcounter.presenter.dto

data class CreateNewRoomRequest(
    val roomName: String,
)

data class RoomDetailResponse(
    val roomId: String,
    val roomName: String,
    val ownerId: String,
    val lastMessageAtTs: Long,
    val unreadMessageCount: Long,
    val createdAtTs: Long,
)
