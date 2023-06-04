package co.bearus.dogsoundcounter.entities

data class SocialLoginUser(
    val userId: String,
    val provider: UserProvider,
    val providerKey: String,
)

enum class UserProvider {
    KAKAO, GOOGLE, APPLE
}
