package co.bearus.dogsoundcounter.entities

data class User(
    val userId: String,
    val email: String,
    val nickname: String,
    val tag: String,
    val profileImgUrl: String = "https://bsc-assets-public.s3.ap-northeast-2.amazonaws.com/default_profile.jpeg",
    val lastSeenAtTs: Long,
    val createdAtTs: Long,
) {
    companion object {
        fun newInstance(
            userId: String,
            email: String,
            nickname: String,
            tag: String,
        ): User {
            return User(
                userId = userId,
                email = email,
                nickname = nickname,
                tag = tag,
                lastSeenAtTs = System.currentTimeMillis(),
                createdAtTs = System.currentTimeMillis(),
            )
        }
    }
}
