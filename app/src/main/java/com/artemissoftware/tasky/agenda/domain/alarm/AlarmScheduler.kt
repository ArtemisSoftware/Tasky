package com.artemissoftware.tasky.agenda.domain.alarm

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface AlarmScheduler {

    fun schedule(item: AgendaItem)
    fun cancel(item: AgendaItem)
}
