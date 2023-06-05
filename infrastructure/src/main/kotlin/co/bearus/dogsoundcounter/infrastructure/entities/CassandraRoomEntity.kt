package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.Room
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("room")
data class CassandraRoomEntity(
    @field:PrimaryKey("room_id")
    val roomId: String,

    @field:Column("room_nm")
    val roomName: String,

    @field:Column("owner_id")
    val ownerId: String,

    @field:Column("created_at")
    val createdAtTs: Timestamp,
) {
    fun toDomain() = Room(
        roomId = roomId,
        roomName = roomName,
        ownerId = ownerId,
        createdAtTs = createdAtTs.time,
    )

    companion object {
        fun fromDomain(room: Room) = CassandraRoomEntity(
            roomId = room.roomId,
            roomName = room.roomName,
            ownerId = room.ownerId,
            createdAtTs = Timestamp(room.createdAtTs),
        )
    }
}
