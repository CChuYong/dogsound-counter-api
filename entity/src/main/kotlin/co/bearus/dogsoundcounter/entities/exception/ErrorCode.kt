package co.bearus.dogsoundcounter.entities.exception

enum class ErrorCode(
    val code: String,
    val message: String,
) {
    USER_NOT_FOUND("UN0001", "사용자를 찾을 수 없습니다"),
    USER_ALREADY_EXISTS("UA0001", "이미 존재하는 사용자입니다"),
    ILLEGAL_TOKEN("IT0001", "인증 정보가 유효하지 않습니다"),
    INCORRECT_CREDENTIALS("IC0001", "아이디 또는 비밀번호가 일치하지 않습니다"),
    ROOM_NAME_VALIDATION("RV0001", "방 이름은 한 글자 이상이어야 합니다."),
    USER_NICKNAME_VALIDATION("UN0002", "사용자 닉네임은 한 글자 이상이어야 합니다."),
    FRIEND_CANNOT_BE_MYSELF("FN0001", "자기 자신과는 친구할 수 없습니다"),
    FRIEND_REQUEST_NOT_VALID("FN0002", "그런 친구에게서 요청이 오지 않았습니다."),
    ALREADY_FRIEND("FN0003", "이미 그사람과는 친구입니다"),
    UNKNOWN_ERROR("UE0001", "알 수 없는 에러가 발생했습니다"),
}