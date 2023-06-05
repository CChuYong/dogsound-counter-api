package co.bearus.dogsoundcounter.entities

data class SocialLoginUser(
    val userId: String,
    val provider: UserProvider,
    val providerKey: String,
) {
    companion object {
        fun newInstance(
            userId: String,
            provider: UserProvider,
            providerKey: String,
        ) = SocialLoginUser(
            userId = userId,
            provider = provider,
            providerKey = providerKey,
        )
    }
}