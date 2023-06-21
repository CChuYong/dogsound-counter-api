package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.RoomUserPrice

interface RoomUserPriceRepository {
    suspend fun findByRoomUser(roomUserId: String, startDay: String): RoomUserPrice?
    suspend fun findAllByRoomUser(roomUserId: String): List<RoomUserPrice>
    suspend fun sumByRoomUser(roomUserId: String): Long
    suspend fun persist(roomUserPrice: RoomUserPrice): RoomUserPrice
}