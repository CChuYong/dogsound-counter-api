package co.bearus.dogsoundcounter.usecases.room

import co.bearus.dogsoundcounter.entities.RoomUserPrice

interface RoomUserPriceRepository {
    suspend fun findByRoomUser(roomUserId: String, startDay: String): RoomUserPrice?
    suspend fun findAllByRoomUser(roomUserId: String): List<RoomUserPrice>
    suspend fun findAllByUser(userId: String, startDay: String): List<RoomUserPrice>
    suspend fun sumByRoomUser(roomUserId: String): Long
    suspend fun cumulateByRoomUser(roomUserId: String, userId: String, startDay: String, price: Int)
    suspend fun persist(roomUserPrice: RoomUserPrice): RoomUserPrice
}