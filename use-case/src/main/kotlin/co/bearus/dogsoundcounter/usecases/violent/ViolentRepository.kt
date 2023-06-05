package co.bearus.dogsoundcounter.usecases.violent

import co.bearus.dogsoundcounter.entities.Violent

interface ViolentRepository {
    suspend fun getById(violentId: String): Violent
    suspend fun persist(violent: Violent): Violent
}