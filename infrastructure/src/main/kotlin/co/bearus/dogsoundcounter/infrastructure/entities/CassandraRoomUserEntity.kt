package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.RoomUser
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("room_user")
data class CassandraRoomUserEntity(
    @field:PrimaryKey("room_user_id")
    val roomUserId: String,

    @field:Column("room_id")
    val roomId: String,

    @field:Column("user_id")
    val userId: String,

    @field:Column("nickname")
    val nickname: String,

    @field:Column("invited_by")
    val invitedBy: String,

    @field:Column("created_at")
    val createdAtTs: Timestamp,
) {
    fun toDomain() = RoomUser(
        roomUserId = roomUserId,
        roomId = roomId,
        userId = userId,
        nickname = nickname,
        invitedBy = invitedBy,
        createdAtTs = createdAtTs.time,
    )

    companion object {
        fun fromDomain(roomUser: RoomUser) = CassandraRoomUserEntity(
            roomUserId = roomUser.roomUserId,
            roomId = roomUser.roomId,
            userId = roomUser.userId,
            nickname = roomUser.nickname,
            invitedBy = roomUser.invitedBy,
            createdAtTs = Timestamp(roomUser.createdAtTs),
        )
    }
}