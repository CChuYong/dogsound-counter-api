package co.bearus.dogsoundcounter.presenter.dto

import co.bearus.dogsoundcounter.entities.User

data class CreateNewUserRequest(
    val email: String,
)

data class UpdateUserNicknameRequest(
    val newNickname: String,
)

data class UserResponse(
    val id: String,
    val email: String,
    val nickname: String,
    val createdAtTs: Long,
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.userId,
            email = user.email,
            nickname = user.nickname,
            createdAtTs = user.createdAtTs,
        )
    }
}
