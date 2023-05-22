package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.Identity
import co.bearus.dogsoundcounter.entities.User
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

data class UserEntity(
    @Id
    @Column("id")
    val id: String,

    @Column("email")
    val email: String,

    @Column("password")
    val password: String,
) {
    fun toDomain() = User(
       identity = Identity.of(0),
       email = email,
       encodedPassword = password,
    )

    companion object {
        fun fromDomain(user: User) = UserEntity(
            id = user.identity.id.toString(),
            email = user.email,
            password = user.encodedPassword,
        )
    }
}
