package co.bearus.dogsoundcounter.presenter.dto

data class CreateNewMessageRequest(
    val violentId: String,
    val speakerId: String,
    val catcherId: String,
    val content: String,
)