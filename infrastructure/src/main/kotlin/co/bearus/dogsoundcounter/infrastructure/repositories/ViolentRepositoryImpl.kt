package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.entities.Violent
import co.bearus.dogsoundcounter.infrastructure.entities.CassandraViolentEntity
import co.bearus.dogsoundcounter.usecases.violent.ViolentRepository
import org.springframework.stereotype.Repository

@Repository
class ViolentRepositoryImpl(
    private val cassandraViolentRepository: CassandraViolentRepository,
): ViolentRepository {
    override suspend fun getById(violentId: String): Violent {
        return cassandraViolentRepository.getByViolentId(violentId).toDomain()
    }

    override suspend fun findAllByRoomId(roomId: String): List<Violent> {
        return cassandraViolentRepository.findAllByRoomId(roomId).map { it.toDomain() }
    }

    override suspend fun persist(violent: Violent): Violent {
        return cassandraViolentRepository.save(
            CassandraViolentEntity.fromDomain(violent)
        ).toDomain()
    }
}