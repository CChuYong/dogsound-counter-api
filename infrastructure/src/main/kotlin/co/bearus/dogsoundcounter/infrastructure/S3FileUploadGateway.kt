package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.usecases.file.FileChannel
import co.bearus.dogsoundcounter.usecases.file.FileUploadGateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.net.URL
import java.time.Duration

@Component
class S3FileUploadGateway(
    @Value("\${app.aws.bucket-name}") private val bucketName: String,
    @Value("\${app.aws.upload-expiration}") private val uploadExpiration: Long,
    private val s3Presigner: S3Presigner,
): FileUploadGateway {
    override suspend fun requestUploadUrl(fileChannel: FileChannel, key: String): URL {
        val actualKey = "${fileChannel.folderPath}/$key"
        val putRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(actualKey)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMillis(uploadExpiration))
            .putObjectRequest(putRequest)
            .build()

        return s3Presigner
            .presignPutObject(presignRequest)
            .url()
    }
}