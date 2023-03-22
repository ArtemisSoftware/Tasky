package com.artemissoftware.tasky.agenda.domain.models

import java.time.LocalDate

data class DayOfWeek(
    val date: LocalDate,
    val description: String = date.dayOfWeek.toString().first().toString(),
    val day: String = date.dayOfMonth.toString()
)
