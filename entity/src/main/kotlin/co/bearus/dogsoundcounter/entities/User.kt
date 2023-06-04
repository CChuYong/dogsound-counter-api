package co.bearus.dogsoundcounter.entities

data class User(
    val userId: String,
    val email: String,
    val nickname: String = "",
    val createdAtTs: Long,
) {
    companion object {
        fun newInstance(
            userId: String,
            email: String,
        ): User {
            return User(
                userId = userId,
                email = email,
                createdAtTs = System.currentTimeMillis(),
            )
        }
    }
}
