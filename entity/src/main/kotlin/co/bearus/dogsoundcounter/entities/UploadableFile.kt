package co.bearus.dogsoundcounter.entities

import java.net.URL

data class UploadableFile(
    val uploadUrl: URL,
    val downloadUrl: URL,
)