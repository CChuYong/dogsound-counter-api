package co.bearus.dogsoundcounter.infrastructure.repositories

import co.bearus.dogsoundcounter.infrastructure.entities.CassandraRoomEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CassandraRoomRepository : CoroutineCrudRepository<CassandraRoomEntity, String>
