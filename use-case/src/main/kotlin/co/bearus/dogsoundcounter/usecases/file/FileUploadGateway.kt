package co.bearus.dogsoundcounter.usecases.file

import java.net.URL

interface FileUploadGateway {
    suspend fun requestUploadUrl(fileChannel: FileChannel, key: String): URL
}