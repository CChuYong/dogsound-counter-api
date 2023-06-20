package co.bearus.dogsoundcounter.entities

data class UserNotificationConfig(
    val userId: String,
    val socialAlert: Boolean, // 친구요청, 수락 등의 경우
    val badSoundAlert: Boolean, // 나쁜말 했을 경우
    val nonBadSoundAlert: Boolean, // 그냥 일반 채팅일 경우
    val noticeAlert: Boolean, //앱 공지사항
) {
    companion object {
        fun createDefault(userId: String) = UserNotificationConfig(
            userId,
            socialAlert = true,
            badSoundAlert = true,
            nonBadSoundAlert = true,
            noticeAlert = true,
        )
    }
}
