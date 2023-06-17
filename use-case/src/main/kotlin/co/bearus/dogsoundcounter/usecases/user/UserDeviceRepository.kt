package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.UserDevice

interface UserDeviceRepository {
    suspend fun getUserDevices(userId: String): List<UserDevice>
    suspend fun findUserDevice(userId: String, fcmToken: String): UserDevice?
    suspend fun persist(userDevice: UserDevice): UserDevice
    suspend fun deleteAllByToken(fcmToken: String)
}
