package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.alarm.AlarmSpec
import com.artemissoftware.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.AgendaItemType
import java.time.LocalDateTime

fun AgendaResponseDto.toAgenda(): List<AgendaItem> {
    return this.reminders.map { it.toReminder() } + this.tasks.map { it.toTask() } + this.events.map { it.toEvent() }
}

fun AgendaItem.toAlarmSpec() : AlarmSpec {
    val agendaItemType = AgendaItemType.convertAgendaItem(this)
    return AlarmSpec(
        id = itemId,
        date = itemRemindAt,
        title = agendaItemType.text,
        body = itemTitle,
        deeplink = agendaItemType.detailDeepLink
    )
}