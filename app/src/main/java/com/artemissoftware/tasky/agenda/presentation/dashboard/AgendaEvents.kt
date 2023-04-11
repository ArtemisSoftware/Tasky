package com.artemissoftware.tasky.agenda.presentation.dashboard

import com.artemissoftware.core.presentation.events.TaskyEvents
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItems
import java.time.LocalDate

sealed class AgendaEvents : TaskyEvents() {
    data class ChangeWeekDay(val date: LocalDate) : AgendaEvents()
    data class CompleteAssignment(val item: AgendaItem.Task) : AgendaEvents()
    data class ChangeDate(val date: LocalDate) : AgendaEvents()

    object LogOut : AgendaEvents()

    data class GoToDetail(val item: AgendaItem, val isEditing: Boolean) : AgendaEvents()

    data class CreateAgendaItem(val detailType: AgendaItems) : AgendaEvents()

    data class Delete(val item: AgendaItem) : AgendaEvents()
}
