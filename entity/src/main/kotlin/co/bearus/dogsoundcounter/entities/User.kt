package co.bearus.dogsoundcounter.entities

import java.time.LocalDateTime

data class User(
    val identity: Identity,
    val email: String,
    val encodedPassword: String,
){
    companion object{
        fun newInstance(
            email: String,
            encodedPassword: String
        ): User {
            return User(
                identity = Identity.NOTHING,
                email = email,
                encodedPassword = encodedPassword,
            )
        }
    }
}