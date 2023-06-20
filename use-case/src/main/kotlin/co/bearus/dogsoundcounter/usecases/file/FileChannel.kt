package co.bearus.dogsoundcounter.usecases.file

enum class FileChannel(
    val folderPath: String,
) {
    USER_PROFILE_IMAGE("user-profile-image"),
    ROOM_PROFILE_IMAGE("room-profile-image"),
}