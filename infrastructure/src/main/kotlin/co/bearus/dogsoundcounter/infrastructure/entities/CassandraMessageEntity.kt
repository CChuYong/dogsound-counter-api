package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.Message
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("message")
data class CassandraMessageEntity(
    @PrimaryKey("messageId")
    val messageId: String,

    @Column("roomId")
    val roomId: String,

    @Column("content")
    val content: String,

    @Column("violentId")
    val violentId: String,

    @Column("violentPrice")
    val violentPrice: Int,

    @Column("speakerId")
    val speakerId: String,

    @Column("catcherId")
    val catcherId: String,

    @Column("createdAtTs")
    val createdAtTs: Long,
) {
    fun toDomain() = Message(
        messageId = messageId,
        roomId = roomId,
        content = content,
        violentId = violentId,
        violentPrice = violentPrice,
        speakerId = speakerId,
        catcherId = catcherId,
        createdAtTs = createdAtTs,
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
            createdAtTs = message.createdAtTs,
        )
    }
}
