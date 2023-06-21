package co.bearus.dogsoundcounter.usecases.user

import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.usecases.LocalizedWeek
import co.bearus.dogsoundcounter.usecases.UseCase
import java.util.*


class GetUserDashboardUseCase(
    private val userRepository: UserRepository,
) : UseCase<GetUserDashboardUseCase.Input, GetUserDashboardUseCase.Output> {
    data class Input(
        val user: User,
    )

    data class Output(
        val weeklyPrice: Int,
        val currentMonth: Int,
        val currentWeek: Int,
        val weekStartDay: Int,
        val weekEndDay: Int,
    )

    override suspend fun execute(input: Input): Output {
        val week = LocalizedWeek(Locale.KOREA)
        return Output(
            weeklyPrice = 0,
            currentMonth = week.month(),
            currentWeek = week.weekOfMonth(),
            weekStartDay = week.firstDay.dayOfMonth,
            weekEndDay = week.lastDay.dayOfMonth,
        )
    }
}
