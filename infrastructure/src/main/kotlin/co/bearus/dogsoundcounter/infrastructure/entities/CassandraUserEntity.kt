package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.User
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("user")
data class CassandraUserEntity(
    @PrimaryKey("userId")
    val userId: String,

    @Column("email")
    val email: String,

    @Column("nickname")
    val nickname: String,

    @Column("createdAtTs")
    val createdAtTs: Long,
) {
    fun toDomain() = User(
        userId = userId,
        email = email,
        nickname = nickname,
        createdAtTs = createdAtTs,
    )

    companion object {
        fun fromDomain(user: User) = CassandraUserEntity(
            userId = user.userId,
            email = user.email,
            nickname = user.nickname,
            createdAtTs = user.createdAtTs,
        )
    }
}
