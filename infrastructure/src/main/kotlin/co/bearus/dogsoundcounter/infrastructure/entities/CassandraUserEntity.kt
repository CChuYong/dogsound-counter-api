package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.User
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("user")
data class CassandraUserEntity(
    @field:PrimaryKey("user_id")
    val userId: String,

    @field:Column("email")
    val email: String,

    @field:Column("nickname")
    val nickname: String,

    @field:Column("tag")
    val tag: String,

    @field:Column("profile_img_url")
    val profileImgUrl: String,

    @field:Column("last_seen_at")
    val lastSeenAtTs: Timestamp,

    @field:Column("created_at")
    val createdAtTs: Timestamp,
) {
    fun toDomain() = User(
        userId = userId,
        email = email,
        nickname = nickname,
        tag = tag,
        profileImgUrl = profileImgUrl,
        lastSeenAtTs = createdAtTs.time,
        createdAtTs = createdAtTs.time,
    )

    companion object {
        fun fromDomain(user: User) = CassandraUserEntity(
            userId = user.userId,
            email = user.email,
            nickname = user.nickname,
            tag = user.tag,
            profileImgUrl = user.profileImgUrl,
            lastSeenAtTs = Timestamp(user.lastSeenAtTs),
            createdAtTs = Timestamp(user.createdAtTs),
        )
    }
}
