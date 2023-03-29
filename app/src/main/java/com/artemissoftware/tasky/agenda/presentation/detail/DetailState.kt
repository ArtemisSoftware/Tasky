package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import java.time.LocalDate
import java.time.LocalTime

data class DetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val agendaItemType: AgendaItemType? = null,
    val agendaItem: AgendaItem? = null,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val endTime: LocalTime = LocalTime.now(),
    val startTime: LocalTime = LocalTime.now(),
)
