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

    @field:Column("profile_img_url")
    val profileImgUrl: String,

    @field:Column("created_at")
    val createdAtTs: Timestamp,
) {
    fun toDomain() = User(
        userId = userId,
        email = email,
        nickname = nickname,
        profileImgUrl = profileImgUrl,
        createdAtTs = createdAtTs.time,
    )

    companion object {
        fun fromDomain(user: User) = CassandraUserEntity(
            userId = user.userId,
            email = user.email,
            nickname = user.nickname,
            profileImgUrl = user.profileImgUrl,
            createdAtTs = Timestamp(user.createdAtTs),
        )
    }
}
