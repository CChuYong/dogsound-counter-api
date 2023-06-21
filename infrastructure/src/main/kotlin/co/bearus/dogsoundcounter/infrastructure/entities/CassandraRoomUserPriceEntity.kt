package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.RoomUserPrice
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("room_user_price")
data class CassandraRoomUserPriceEntity(
    @field:PrimaryKey("room_user_id")
    val roomUserId: String,

    @field:Column("start_day")
    val startDay: String,

    @field:Column("user_id")
    val userId: String,

    @field:Column("cumulated_price")
    val cumulatedPrice: Int,
) {
    companion object {
        fun fromDomain(roomUserPrice: RoomUserPrice) = CassandraRoomUserPriceEntity(
            roomUserId = roomUserPrice.roomUserId,
            startDay = roomUserPrice.startDay,
            userId = roomUserPrice.userId,
            cumulatedPrice = roomUserPrice.cumulatedPrice,
        )
    }

    fun toDomain() = RoomUserPrice(
        roomUserId = roomUserId,
        startDay = startDay,
        userId = userId,
        cumulatedPrice = cumulatedPrice,
    )
}