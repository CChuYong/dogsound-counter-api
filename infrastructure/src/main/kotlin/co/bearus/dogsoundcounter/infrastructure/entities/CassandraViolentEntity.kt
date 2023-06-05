package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.Violent
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("violent")
data class CassandraViolentEntity(
    @field:PrimaryKey("violent_id")
    val violentId: String,

    @field:Column("room_id")
    val roomId: String,

    @field:Column("name")
    val name: String,

    @field:Column("description")
    val description: String,

    @field:Column("violent_price")
    val violentPrice: Int,

    @field:Column("created_user_id")
    val createdUserId: String,

    @field:Column("created_at")
    val createdAtTs: Timestamp,
) {
    fun toDomain() = Violent(
        violentId = violentId,
        roomId = roomId,
        name = name,
        description = description,
        violentPrice = violentPrice,
        createdUserId = createdUserId,
        createdAtTs = createdAtTs.time,
    )

    companion object {
        fun fromDomain(violent: Violent) = CassandraViolentEntity(
            violentId = violent.violentId,
            roomId = violent.roomId,
            name = violent.name,
            description = violent.description,
            violentPrice = violent.violentPrice,
            createdUserId = violent.createdUserId,
            createdAtTs = Timestamp(violent.createdAtTs),
        )
    }
}
