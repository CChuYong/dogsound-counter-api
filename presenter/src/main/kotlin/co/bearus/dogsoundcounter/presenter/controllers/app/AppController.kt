package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.dto.FileUploadResponse
import co.bearus.dogsoundcounter.usecases.IdentityGenerator
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
    private val identityGenerator: IdentityGenerator,
) {
    @GetMapping("/upload-request", params = ["type"])
    suspend fun requestUserUploadUrl(
        @RequestParam type: String,
    ): FileUploadResponse {
        val url = fileUploadGateway.requestUploadUrl(
            fileChannel = getFileChannel(type),
            key = identityGenerator.createIdentity(),
        )
        return FileUploadResponse(
            uploadUrl = url.uploadUrl.toExternalForm(),
            downloadUrl = url.downloadUrl.toExternalForm(),
        )
    }

    private fun getFileChannel(type: String) = when (type) {
        "USER" -> FileChannel.USER_PROFILE_IMAGE
        "ROOM" -> FileChannel.ROOM_PROFILE_IMAGE
        else -> throw IllegalArgumentException("Invalid type: $type")
    }
}
