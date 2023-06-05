package co.bearus.dogsoundcounter.usecases.violent

import co.bearus.dogsoundcounter.entities.Violent

interface ViolentRepository {
    suspend fun getById(violentId: String): Violent
    suspend fun findAllByRoomId(roomId: String): List<Violent>
    suspend fun persist(violent: Violent): Violent
}