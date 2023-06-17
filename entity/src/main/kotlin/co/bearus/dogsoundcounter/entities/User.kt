package co.bearus.dogsoundcounter.entities

data class User(
    val userId: String,
    val email: String,
    val nickname: String = MockNickGenerator.generate(),
    val profileImgUrl: String = "https://bsc-assets.s3.ap-northeast-2.amazonaws.com/default_profile_img.webp",
    val lastSeenAtTs: Long,
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
                lastSeenAtTs = System.currentTimeMillis(),
                createdAtTs = System.currentTimeMillis(),
            )
        }
    }
}
