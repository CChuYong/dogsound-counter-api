package co.bearus.dogsoundcounter.usecases

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

class LocalizedWeek(private val locale: Locale) {
    private val now = LocalDate.now(TZ)
    private val weekField = WeekFields.of(locale)
    private val firstDayOfWeek: DayOfWeek = WeekFields.of(locale).firstDayOfWeek
    private val lastDayOfWeek: DayOfWeek = DayOfWeek.of((firstDayOfWeek.value + 5) % DayOfWeek.values().size + 1)

    fun weekOfMonth() = now.get(weekField.weekOfMonth())
    fun month() = now.monthValue
    val firstDay: LocalDate
        get() = now.with(
            TemporalAdjusters.previousOrSame(
                firstDayOfWeek
            )
        )
    val lastDay: LocalDate
        get() = now.with(
            TemporalAdjusters.nextOrSame(
                lastDayOfWeek
            )
        )

    override fun toString(): String {
        return java.lang.String.format(
            "The %s week starts on %s and ends on %s",
            locale.displayName,
            firstDayOfWeek,
            lastDayOfWeek
        )
    }

    companion object {
        private val TZ = ZoneId.of("Asia/Seoul")
    }
}