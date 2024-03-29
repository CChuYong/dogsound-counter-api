package co.bearus.dogsoundcounter.infrastructure.entities

import co.bearus.dogsoundcounter.entities.UserNotificationConfig
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("user_notification_config")
class CassandraNotificationConfigEntity(
    @field:PrimaryKey("user_id")
    val userId: String,

    @field:Column("social_alert")
    val socialAlert: Boolean,

    @field:Column("bad_sound_alert")
    val badSoundAlert: Boolean,

    @field:Column("non_bad_sound_alert")
    val nonBadSoundAlert: Boolean,

    @field:Column("notice_alert")
    val noticeAlert: Boolean,
) {
    fun toDomain() = UserNotificationConfig(
        userId = userId,
        socialAlert = socialAlert,
        badSoundAlert = badSoundAlert,
        nonBadSoundAlert = nonBadSoundAlert,
        noticeAlert = noticeAlert,
    )

    companion object {
        fun fromDomain(userNotificationConfig: UserNotificationConfig) = CassandraNotificationConfigEntity(
            userId = userNotificationConfig.userId,
            socialAlert = userNotificationConfig.socialAlert,
            badSoundAlert = userNotificationConfig.badSoundAlert,
            nonBadSoundAlert = userNotificationConfig.nonBadSoundAlert,
            noticeAlert = userNotificationConfig.noticeAlert,
        )
    }
}
