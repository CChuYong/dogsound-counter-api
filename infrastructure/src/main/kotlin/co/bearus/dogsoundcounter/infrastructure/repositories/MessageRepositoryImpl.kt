package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.Message
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraMessageEntity
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class MessageRepositoryImpl(
    private val cassandraMessageRepository: CassandraMessageRepository,
): MessageRepository {
    override suspend fun persist(message: Message): Message {
        return cassandraMessageRepository.save(
            CassandraMessageEntity.fromDomain(message)
        ).toDomain()
    }

    override suspend fun findMessageByRoomId(roomId: String): List<Message> {
        return cassandraMessageRepository.findAllByRoomId(roomId).map { it.toDomain() }
    }

    override suspend fun findMessageByRoomIdAfter(roomId: String, messageId: String, limit: Int): List<Message> {
        return cassandraMessageRepository.findAllByRoomIdAndMessageIdGreaterThan(
            roomId,
            messageId,
            PageRequest.of(0, limit)
        ).map { it.toDomain() }
    }

    override suspend fun findMessageByRoomIdBefore(roomId: String, messageId: String, limit: Int): List<Message> {
        return cassandraMessageRepository.findAllByRoomIdAndMessageIdLessThan(
            roomId,
            messageId,
            PageRequest.of(0, limit)
        ).map { it.toDomain() }
    }

    override suspend fun countUnreadMessage(roomId: String, messageId: String): Long {
        return cassandraMessageRepository.countByRoomIdAndMessageIdGreaterThan(roomId, messageId = messageId)
    }

    override suspend fun getLastMessage(roomId: String): Message? {
        return cassandraMessageRepository.findFirstByRoomIdOrderByMessageIdDesc(roomId)?.toDomain()
    }
}
