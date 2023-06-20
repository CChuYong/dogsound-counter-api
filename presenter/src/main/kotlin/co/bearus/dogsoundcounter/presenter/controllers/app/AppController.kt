package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.FileUploadResponse
import co.bearus.dogsoundcounter.usecases.file.FileChannel
import co.bearus.dogsoundcounter.usecases.file.FileUploadGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/app")
class AppController(
    private val fileUploadGateway: FileUploadGateway,
) {
    @GetMapping("/upload-request", params = ["type=USER"])
    suspend fun requestUserUploadUrl(
        @RequestUser user: LoginUser,
    ): FileUploadResponse {
        val url = fileUploadGateway.requestUploadUrl(
            fileChannel = FileChannel.USER_PROFILE_IMAGE,
            key = user.userId
        )
        return FileUploadResponse(
            uploadUrl = url.toExternalForm()
        )
    }

    @GetMapping("/upload-request", params = ["type=ROOM", "roomId"])
    suspend fun requestRoomUploadUrl(
        @RequestParam roomId: String,
    ): FileUploadResponse {
        val url = fileUploadGateway.requestUploadUrl(
            fileChannel = FileChannel.ROOM_PROFILE_IMAGE,
            key = roomId,
        )
        return FileUploadResponse(
            uploadUrl = url.toExternalForm()
        )
    }
}