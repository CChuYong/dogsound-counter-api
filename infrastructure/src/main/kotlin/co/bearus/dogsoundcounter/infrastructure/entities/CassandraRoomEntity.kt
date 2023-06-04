package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.Room
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("room")
data class CassandraRoomEntity(
    @PrimaryKey("roomId")
    val roomId: String,

    @Column("roomName")
    val roomName: String,

    @Column("ownerId")
    val ownerId: String,

    @Column("createdAtTs")
    val createdAtTs: Long,
) {
    fun toDomain() = Room(
        roomId = roomId,
        roomName = roomName,
        ownerId = ownerId,
        createdAtTs = createdAtTs,
    )

    companion object {
        fun fromDomain(room: Room) = CassandraRoomEntity(
            roomId = room.roomId,
            roomName = room.roomName,
            ownerId = room.ownerId,
            createdAtTs = room.createdAtTs,
        )
    }
}
