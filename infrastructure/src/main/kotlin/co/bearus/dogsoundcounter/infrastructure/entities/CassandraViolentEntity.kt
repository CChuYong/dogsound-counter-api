package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.Violent
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("violent")
data class CassandraViolentEntity(
    @PrimaryKey("violent_id")
    val violentId: String,

    @Column("room_id")
    val roomId: String,

    @Column("name")
    val name: String,

    @Column("description")
    val description: String,

    @Column("violent_price")
    val violentPrice: Int,

    @Column("created_user_id")
    val createdUserId: String,

    @Column("created_at")
    val createdAtTs: Long,
) {
    fun toDomain() = Violent(
        violentId = violentId,
        roomId = roomId,
        name = name,
        description = description,
        violentPrice = violentPrice,
        createdUserId = createdUserId,
        createdAtTs = createdAtTs,
    )

    companion object {
        fun fromDomain(violent: Violent) = CassandraViolentEntity(
            violentId = violent.violentId,
            roomId = violent.roomId,
            name = violent.name,
            description = violent.description,
            violentPrice = violent.violentPrice,
            createdUserId = violent.createdUserId,
            createdAtTs = violent.createdAtTs,
        )
    }
}
