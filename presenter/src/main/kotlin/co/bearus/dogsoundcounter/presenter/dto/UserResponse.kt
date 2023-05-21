package co.bearus.dogsoundcounter.presenter.dto

import co.bearus.dogsoundcounter.entities.User

data class CreateNewUserRequest(
    val identifier: String,
    val email: String,
    val password: String,
)

data class UserResponse(
    val email: String,
) {
    companion object {
        fun from(user: User) = UserResponse(
            email = user.email
        )
    }
}
