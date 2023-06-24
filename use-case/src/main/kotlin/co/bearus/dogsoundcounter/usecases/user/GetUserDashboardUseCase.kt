package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.LocalizedWeek
import co.bearus.dogsoundcounter.usecases.UseCase
import co.bearus.dogsoundcounter.usecases.room.RoomUserPriceRepository
import co.bearus.dogsoundcounter.usecases.room.RoomUserRepository
import java.time.LocalDate
import java.util.*


class GetUserDashboardUseCase(
    private val roomUserPriceRepository: RoomUserPriceRepository,
    private val roomUserRepository: RoomUserRepository,
) : UseCase<GetUserDashboardUseCase.Input, GetUserDashboardUseCase.Output> {
    data class Input(
        val user: User,
        val weekDay: LocalDate? = null,
    )

    data class Output(
        val weeklyPrice: Int,
        val currentMonth: Int,
        val currentWeek: Int,
        val weekStartDay: Int,
        val weekEndDay: Int,
    )

    override suspend fun execute(input: Input): Output {
        val week = LocalizedWeek(
            Locale.KOREA,
            now = input.weekDay ?: LocalDate.now(LocalizedWeek.TZ)
        )
        val users = roomUserRepository.findByUserId(input.user.userId).mapNotNull {
            roomUserPriceRepository.findByRoomUser(it.roomUserId, week.firstDay.toString())
        }
        val sum = users.sumOf { it.cumulatedPrice }
        return Output(
            weeklyPrice = sum,
            currentMonth = week.month(),
            currentWeek = week.weekOfMonth(),
            weekStartDay = week.firstDay.dayOfMonth,
            weekEndDay = week.lastDay.dayOfMonth,
        )
    }
}
