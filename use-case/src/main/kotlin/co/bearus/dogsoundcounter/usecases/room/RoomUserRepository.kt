package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.RoomUser

interface RoomUserRepository {
    suspend fun findByUserId(userId: String): List<RoomUser>
    suspend fun findByRoomId(roomId: String): List<RoomUser>
    suspend fun findRoomUser(roomId: String, userId: String): RoomUser
    suspend fun persist(roomUser: RoomUser): RoomUser
}
