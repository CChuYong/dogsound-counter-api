package co.bearus.dogsoundcounter.entities

data class OAuthResult(
    val id: String,
    val email: String,
    val name: String? = null,
    val provider: UserProvider,
)
