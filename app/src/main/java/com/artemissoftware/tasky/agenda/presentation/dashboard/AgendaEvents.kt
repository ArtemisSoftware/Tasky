package com.artemissoftware.tasky.agenda.presentation.dashboard

import com.artemissoftware.core.presentation.events.TaskyEvents
import java.time.LocalDate

sealed class AgendaEvents : TaskyEvents() {
    data class ChangeWeekDay(val date: LocalDate) : AgendaEvents()
    data class CompleteAssignment(val id: String) : AgendaEvents()

    data class ChangeDate(val date: LocalDate) : AgendaEvents()

    object LogOut : AgendaEvents()
}
