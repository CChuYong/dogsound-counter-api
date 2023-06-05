package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.Room

interface RoomRepository {
    suspend fun persist(room: Room): Room
}