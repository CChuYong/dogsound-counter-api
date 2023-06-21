package co.bearus.dogsoundcounter.infrastructure.entities

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("user_friend")
data class CassandraUserFriendEntity(
    @field:PrimaryKey("user_id_1")
    val userId1: String,

    @field:Column("user_id_2")
    val userId2: String,

    @field:Column("created_at")
    val createdAtTs: Timestamp,
)