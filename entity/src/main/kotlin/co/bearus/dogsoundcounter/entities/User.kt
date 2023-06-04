package co.bearus.dogsoundcounter.entities

data class User(
    val userId: String,
    val email: String,
    val encodedPassword: String,
    val createdAtTs: Long,
){
    companion object{
        fun newInstance(
            userId: String,
            email: String,
            encodedPassword: String
        ): User {
            return User(
                userId = userId,
                email = email,
                encodedPassword = encodedPassword,
                createdAtTs = System.currentTimeMillis(),
            )
        }
    }
}
