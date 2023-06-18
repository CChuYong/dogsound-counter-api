package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.UserDevice
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateUserDeviceUseCase(
    private val userDeviceRepository: UserDeviceRepository,
) : UseCase<CreateUserDeviceUseCase.Input, UserDevice> {
    data class Input(
        val user: User,
        val fcmToken: String,
        val deviceInfo: String,
    )

    override suspend fun execute(input: Input): UserDevice {
        val previousDevice = userDeviceRepository.findUserDevice(input.user.userId, input.fcmToken)
        if (previousDevice != null) return previousDevice

        val userDevice = UserDevice(
            userId = input.user.userId,
            fcmToken = input.fcmToken,
            deviceInfo = input.deviceInfo,
            createdAtTs = System.currentTimeMillis()
        )
        return userDeviceRepository.persist(userDevice)
    }
}
