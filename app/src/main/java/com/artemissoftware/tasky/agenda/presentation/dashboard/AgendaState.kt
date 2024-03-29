package com.artemissoftware.tasky.agenda.presentation.dashboard

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.DayOfWeek
import java.time.LocalDate

data class AgendaState(
    val isLoading: Boolean = false,
    val agendaItems: List<AgendaItem> = emptyList(),
    val daysOfTheWeek: List<DayOfWeek> = emptyList(),
    val selectedDayOfTheWeek: LocalDate = LocalDate.now(),
    val userName: String = "",
    val userId: String = "",
    val needlePosition: String = "",
)
