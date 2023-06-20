package co.bearus.dogsoundcounter.usecases.file

import co.bearus.dogsoundcounter.entities.UploadableFile

interface FileUploadGateway {
    suspend fun requestUploadUrl(fileChannel: FileChannel, key: String): UploadableFile
}