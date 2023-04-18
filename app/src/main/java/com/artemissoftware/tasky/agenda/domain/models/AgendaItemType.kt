package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.R

enum class AgendaItemType(val text: UiText) {

    EVENT(UiText.StringResource(R.string.event)),
    REMINDER(UiText.StringResource(R.string.reminder)),
    TASK(UiText.StringResource(R.string.task));

    companion object {
        fun convertAgendaItem(agendaItem: AgendaItem): AgendaItemType {
            return when (agendaItem) {
                is AgendaItem.Reminder -> REMINDER
                is AgendaItem.Task -> TASK
                is AgendaItem.Event -> EVENT
            }
        }
    }
}
