package com.artemissoftware.tasky.agenda.presentation.mappers

import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

fun AgendaItem.toAgendaItemType(): AgendaItemType{
    return when(this){
        is AgendaItem.Reminder -> {
            AgendaItemType.Reminder()
        }
    }
}