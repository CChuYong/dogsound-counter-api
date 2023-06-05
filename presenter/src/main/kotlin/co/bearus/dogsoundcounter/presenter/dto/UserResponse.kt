package co.bearus.dogsoundcounter.presenter.dto

import co.bearus.dogsoundcounter.entities.User

data class CreateNewUserRequest(
    val email: String,
)

data class UserResponse(
    val id: String,
    val email: String,
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.userId,
            email = user.email
        )
    }
}
