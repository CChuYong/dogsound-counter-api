package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.RoomUser

interface RoomUserRepository {
    suspend fun findByUserId(userId: String): List<RoomUser>
    suspend fun persist(roomUser: RoomUser): RoomUser
}