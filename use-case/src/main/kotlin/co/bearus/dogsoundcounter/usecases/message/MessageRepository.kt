package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.Message

interface MessageRepository {
    suspend fun persist(message: Message): Message
    suspend fun findMessageByRoomId(roomId: String, limit: Int): List<Message>
    suspend fun findMessageByRoomIdAfter(roomId: String, messageId: String, limit: Int): List<Message>
    suspend fun findMessageByRoomIdBefore(roomId: String, messageId: String, limit: Int): List<Message>
    suspend fun countUnreadMessage(roomId: String, messageId: String): Long
    suspend fun getLastMessage(roomId: String): Message?
}
