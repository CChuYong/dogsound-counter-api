package co.bearus.dogsoundcounter.entities

import java.time.LocalDateTime

data class User(
    val identity: Identity,
    val email: String,
    val encodedPassword: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)