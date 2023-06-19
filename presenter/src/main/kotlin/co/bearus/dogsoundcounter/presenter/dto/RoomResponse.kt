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
    val roomImageUrl: String,
    val createdAtTs: Long,
)

data class RoomResponse(
    val roomId: String,
    val roomName: String,
    val ownerId: String,
    val createdAtTs: Long,
)
