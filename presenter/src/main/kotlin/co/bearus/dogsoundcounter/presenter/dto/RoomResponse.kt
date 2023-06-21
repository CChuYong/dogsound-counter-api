package co.bearus.dogsoundcounter.presenter.dto

data class CreateNewRoomRequest(
    val roomName: String,
    val roomImageUrl: String,
    val initialUserIds: List<String>,
)

data class RoomDetailResponse(
    val roomId: String,
    val roomName: String,
    val ownerId: String,
    val lastMessageAtTs: Long,
    val unreadMessageCount: Long,
    val roomImageUrl: String,
    val cumulatedPrice: Long,
    val createdAtTs: Long,
)

data class RoomResponse(
    val roomId: String,
    val roomName: String,
    val ownerId: String,
    val createdAtTs: Long,
)

data class UpdateRoomImageRequest(
    val imageUrl: String,
)
