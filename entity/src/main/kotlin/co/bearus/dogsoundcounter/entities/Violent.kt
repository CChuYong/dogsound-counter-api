package co.bearus.dogsoundcounter.entities

/**
 * 나쁜말 정의
 */
data class Violent(
    val violentId: String,
    val roomId: String,
    val name: String,
    val description: String,
    val violentPrice: Int,
    val createdUserId: String,
    val createdAtTs: Long,
)
