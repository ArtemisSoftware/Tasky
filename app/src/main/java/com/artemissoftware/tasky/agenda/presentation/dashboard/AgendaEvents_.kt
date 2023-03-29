package com.artemissoftware.tasky.agenda.presentation.dashboard

import com.artemissoftware.core.presentation.events.TaskyEvents
import java.time.LocalDate

sealed class AgendaEvents_ : TaskyEvents() {
    data class ChangeWeekDay(val date: LocalDate) : AgendaEvents_()
    data class CompleteAssignment(val id: String) : AgendaEvents_()

    data class ChangeDate(val date: LocalDate) : AgendaEvents_()

    object LogOut : AgendaEvents_()
}
