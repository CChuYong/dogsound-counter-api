package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.Message
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("message")
data class CassandraMessageEntity(
    @field:PrimaryKey("message_id")
    val messageId: String,

    @field:Column("room_id")
    val roomId: String,

    @field:Column("content")
    val content: String,

    @field:Column("violent_id")
    val violentId: String,

    @field:Column("violent_price")
    val violentPrice: Int,

    @field:Column("speaker_id")
    val speakerId: String,

    @field:Column("catcher_id")
    val catcherId: String,

    @field:Column("created_at")
    val createdAtTs: Timestamp,
) {
    fun toDomain() = Message(
        messageId = messageId,
        roomId = roomId,
        content = content,
        violentId = violentId,
        violentPrice = violentPrice,
        speakerId = speakerId,
        catcherId = catcherId,
        createdAtTs = createdAtTs.time,
    )

    companion object {
        fun fromDomain(message: Message) = CassandraMessageEntity(
            messageId = message.messageId,
            roomId = message.roomId,
            content = message.content,
            violentId = message.violentId,
            violentPrice = message.violentPrice,
            speakerId = message.speakerId,
            catcherId = message.catcherId,
            createdAtTs = Timestamp(message.createdAtTs),
        )
    }
}
