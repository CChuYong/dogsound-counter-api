package co.bearus.dogsoundcounter.entities

data class Message(
    val messageId: String,
    val roomId: String,

    val content: String,
    val violentId: String,
    val violentPrice: Int,

    val speakerId: String,
    val catcherId: String,
    val createdAtTs: Long,
)
