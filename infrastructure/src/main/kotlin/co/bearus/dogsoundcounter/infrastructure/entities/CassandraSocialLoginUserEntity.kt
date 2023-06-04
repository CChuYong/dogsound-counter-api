package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.SocialLoginUser
import co.bearus.dogsoundcounter.entities.UserProvider
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("user_social")
data class CassandraSocialLoginUserEntity(
    @PrimaryKey("userId")
    val userId: String,

    @Column("provider")
    val provider: String,

    @Column("providerKey")
    val providerKey: String,
) {
    fun toDomain() = SocialLoginUser(
        userId = userId,
        provider = UserProvider.valueOf(provider),
        providerKey = providerKey,
    )

    companion object {
        fun fromDomain(socialLoginUser: SocialLoginUser) = CassandraSocialLoginUserEntity(
            userId = socialLoginUser.userId,
            provider = socialLoginUser.provider.name,
            providerKey = socialLoginUser.providerKey,
        )
    }
}
