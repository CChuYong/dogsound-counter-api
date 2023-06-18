package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.entities.ClientPacket
import co.bearus.dogsoundcounter.usecases.MessagePublisher
import co.bearus.dogsoundcounter.usecases.user.UserDeviceRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import org.springframework.stereotype.Component


@Component
class FirebaseMessagePublisher(
    private val userDeviceRepository: UserDeviceRepository,
    private val objectMapper: ObjectMapper,
    private val firebaseMessaging: FirebaseMessaging,
) : MessagePublisher {
    override suspend fun publishMessage(userId: String, message: ClientPacket): Boolean {
        val devices = userDeviceRepository.getUserDevices(userId)
        sendDataMulti(devices.map { it.fcmToken }, message)
        return true
    }

    fun sendDataMulti(fcmTokens: List<String>, payload: ClientPacket) {
        val message: MulticastMessage = MulticastMessage.builder()
            .putData("packetType", payload.packetType.name)
            .putData("payload", objectMapper.writeValueAsString(payload.payload))
            .addAllTokens(fcmTokens)
            .setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setContentAvailable(true).build()).build())
            .build()
        try {
            firebaseMessaging.sendMulticastAsync(message)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
