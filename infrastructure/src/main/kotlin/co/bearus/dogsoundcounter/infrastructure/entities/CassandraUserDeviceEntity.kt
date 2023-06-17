package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.UserDevice
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.sql.Timestamp

@Table("user_device")
data class CassandraUserDeviceEntity(
    @field:PrimaryKey("user_id")
    val userId: String,

    @field:Column("fcm_token")
    val fcmToken: String,

    @field:Column("device_info")
    val deviceInfo: String,

    @field:Column("created_at")
    val createdAt: Timestamp,
) {
    fun toDomain() = UserDevice(
        userId = userId,
        fcmToken = fcmToken,
        deviceInfo = deviceInfo,
        createdAtTs = createdAt.time,
    )

    companion object {
        fun fromDomain(userDevice: UserDevice) = CassandraUserDeviceEntity(
            userId = userDevice.userId,
            fcmToken = userDevice.fcmToken,
            deviceInfo = userDevice.deviceInfo,
            createdAt = Timestamp(userDevice.createdAtTs),
        )
    }
}
