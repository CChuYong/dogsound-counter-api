package co.bearus.dogsoundcounter.entities

import java.time.LocalDateTime

data class Message(
    val identity: Identity,
    val content: String,
    val speaker: User,
    val victim: User,
    val capturedReason: CapturedReason,
    val createdAt: LocalDateTime,
)