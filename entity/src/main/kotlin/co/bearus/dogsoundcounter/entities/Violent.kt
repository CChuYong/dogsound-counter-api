package co.bearus.dogsoundcounter.entities

/**
 * 나쁜말 정의
 */
data class  Violent(
    val violentId: String,
    val roomId: String,
    val name: String,
    val description: String,
    val violentPrice: Int,
    val createdUserId: String,
    val createdAtTs: Long,
) {
    companion object {
        fun newInstance(
            violentId: String,
            roomId: String,
            name: String,
            description: String,
            violentPrice: Int,
            createdUserId: String,
        ): Violent {
            return Violent(
                violentId = violentId,
                roomId = roomId,
                name = name,
                description = description,
                violentPrice = violentPrice,
                createdUserId = createdUserId,
                createdAtTs = System.currentTimeMillis(),
            )
        }
    }
}
