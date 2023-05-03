package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.util.NavigationConstants.EVENT_DETAIL_ROUTE
import com.artemissoftware.tasky.agenda.util.NavigationConstants.REMINDER_DETAIL_ROUTE
import com.artemissoftware.tasky.agenda.util.NavigationConstants.TASK_DETAIL_ROUTE

sealed class AgendaItemType(
    val text: UiText,
    val detailDeepLink: String,
) {
    object Reminder : AgendaItemType(text = UiText.StringResource(R.string.reminder), detailDeepLink = REMINDER_DETAIL_ROUTE)
    object Task : AgendaItemType(text = UiText.StringResource(R.string.task), detailDeepLink = TASK_DETAIL_ROUTE)
    object Event : AgendaItemType(text = UiText.StringResource(R.string.event), detailDeepLink = EVENT_DETAIL_ROUTE)

    companion object {
        fun convertAgendaItem(agendaItem: AgendaItem): AgendaItemType {
            return when (agendaItem) {
                is AgendaItem.Reminder -> Reminder
                is AgendaItem.Task -> Task
                is AgendaItem.Event -> Event
            }
        }
    }
}
