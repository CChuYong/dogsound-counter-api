package co.bearus.dogsoundcounter.presenter.dto

import co.bearus.dogsoundcounter.entities.User

data class CreateNewUserRequest(
    val email: String,
)

data class UpdateUserNicknameRequest(
    val newNickname: String,
)

data class UpdateDeviceInfoRequest(
    val fcmToken: String,
    val deviceInfo: String,
)

data class UserResponse(
    val id: String,
    val email: String,
    val nickname: String,
    val taggedNickname: String,
    val profileImgUrl: String,
    val createdAtTs: Long,
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.userId,
            email = user.email,
            nickname = user.nickname,
            taggedNickname = "${user.nickname}#${user.tag}",
            profileImgUrl = user.profileImgUrl,
            createdAtTs = user.createdAtTs,
        )
    }
}

data class CreateNewFriendRequest(
    val tag: String,
)

data class BreakFriendRequest(
    val userId: String,
)

data class UpdateNotificationRequest(
    val type: String,
    val value: Boolean,
)

data class UpdateUserProfileImageRequest(
    val imageUrl: String,
)
