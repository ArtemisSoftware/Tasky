package com.artemissoftware.core.domain.models.agenda

import com.artemissoftware.core.R
import com.artemissoftware.core.util.UiText
import java.time.Duration
import java.time.LocalDateTime

enum class NotificationType(val minutesBefore: Long, val description: UiText) {

    TEN_MINUTES_BEFORE(
        minutesBefore = 10,
        description = UiText.StringResource(R.string.ten_minutes_before),
    ),

    THIRTY_MINUTES_BEFORE(
        minutesBefore = 30,
        description = UiText.StringResource(R.string.thirty_minutes_before),
    ),
    ONE_HOUR_BEFORE(
        minutesBefore = 60,
        description = UiText.StringResource(R.string.one_hour_before),
    ),
    SIX_HOURS_BEFORE(
        minutesBefore = 6 * 60,
        description = UiText.StringResource(R.string.six_hours_before),
    ),
    ONE_DAY_BEFORE(
        minutesBefore = 24 * 60,
        description = UiText.StringResource(R.string.one_day_before),
    ),
    ;

    companion object {

        fun defaultNotification() = THIRTY_MINUTES_BEFORE

        fun remindAt(time: LocalDateTime, notificationType: NotificationType): LocalDateTime {
            return when (notificationType) {
                TEN_MINUTES_BEFORE -> time.minusMinutes(TEN_MINUTES_BEFORE.minutesBefore)
                THIRTY_MINUTES_BEFORE -> time.minusMinutes(THIRTY_MINUTES_BEFORE.minutesBefore)
                ONE_HOUR_BEFORE -> time.minusHours(ONE_HOUR_BEFORE.minutesBefore)
                SIX_HOURS_BEFORE -> time.minusHours(SIX_HOURS_BEFORE.minutesBefore)
                ONE_DAY_BEFORE -> time.minusDays(ONE_DAY_BEFORE.minutesBefore)
            }
        }

        fun getNotification(remindAt: LocalDateTime, startDate: LocalDateTime): NotificationType {
            val differenceInMinutes = Duration.between(remindAt, startDate).toMinutes()
            return values().find { differenceInMinutes == it.minutesBefore } ?: defaultNotification()
        }
    }
}
