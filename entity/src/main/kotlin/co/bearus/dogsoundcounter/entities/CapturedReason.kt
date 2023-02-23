package co.bearus.dogsoundcounter.entities

import java.time.LocalDateTime

data class CapturedReason(
    val identity: Identity,
    val reasonName: String,
    val reasonDescription: String,
    val penaltyPrice: Int,
    val createdAt: LocalDateTime,
)