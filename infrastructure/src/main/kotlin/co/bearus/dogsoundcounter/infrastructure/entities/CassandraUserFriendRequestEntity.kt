package co.bearus.dogsoundcounter.infrastructure.entities

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("user_friend_request")
data class CassandraUserFriendRequestEntity(
    @field:PrimaryKey("from_user_id")
    val fromUserId: String,

    @field:Column("to_user_id")
    val toUserId: String,

    @field:Column("created_at")
    val createdAtTs: Timestamp,
)