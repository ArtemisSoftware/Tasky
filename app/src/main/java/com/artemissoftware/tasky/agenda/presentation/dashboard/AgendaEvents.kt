package com.artemissoftware.tasky.agenda.presentation.dashboard

import com.artemissoftware.core.presentation.events.TaskyEvents
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItems
import java.time.LocalDate

sealed class AgendaEvents : TaskyEvents() {
    data class ChangeWeekDay(val date: LocalDate) : AgendaEvents()
    data class CompleteAssignment(val id: String) : AgendaEvents()
    data class ChangeDate(val date: LocalDate) : AgendaEvents()

    object LogOut : AgendaEvents()

    data class GoToDetail(val id: String? = null, val detailType: AgendaItems, val isEditing: Boolean) : AgendaEvents()

    data class Delete(val id: String) : AgendaEvents()
}
