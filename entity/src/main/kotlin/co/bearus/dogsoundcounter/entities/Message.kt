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
) {
    companion object {
        fun newInstance(
            messageId: String,
            roomId: String,
            content: String,
            violentId: String,
            violentPrice: Int,
            speakerId: String,
            catcherId: String,
        ): Message {
            return Message(
                messageId = messageId,
                roomId = roomId,
                content = content,
                violentId = violentId,
                violentPrice = violentPrice,
                speakerId = speakerId,
                catcherId = catcherId,
                createdAtTs = System.currentTimeMillis(),
            )
        }
    }
}
