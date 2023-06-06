package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.SocialLoginUser
import co.bearus.dogsoundcounter.entities.UserProvider
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("user_social")
data class CassandraSocialLoginUserEntity(
    @field:PrimaryKey("user_id")
    val userId: String,

    @field:Column("provider")
    val provider: String,

    @field:Column("provider_key")
    val providerKey: String,

    @field:Column("created_at")
    val createdAt: Timestamp,
) {
    fun toDomain() = SocialLoginUser(
        userId = userId,
        provider = UserProvider.valueOf(provider),
        providerKey = providerKey,
        createdAtTs = createdAt.time,
    )

    companion object {
        fun fromDomain(socialLoginUser: SocialLoginUser) = CassandraSocialLoginUserEntity(
            userId = socialLoginUser.userId,
            provider = socialLoginUser.provider.name,
            providerKey = socialLoginUser.providerKey,
            createdAt = Timestamp(socialLoginUser.createdAtTs),
        )
    }
}
