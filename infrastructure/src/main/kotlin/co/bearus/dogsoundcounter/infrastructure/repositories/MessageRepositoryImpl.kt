package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.Message
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraMessageEntity
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
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
}