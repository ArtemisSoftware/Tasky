package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import com.artemissoftware.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.artemissoftware.tasky.agenda.domain.models.Agenda
import com.artemissoftware.tasky.agenda.domain.models.Notification

fun NotificationWarningEntity.toNotification(): Notification {
    return Notification(
        minutesBefore = minutesBefore,
        description = description,
        isDefault = isDefault,
        id = id,
    )
}

fun AgendaResponseDto.toAgenda(): Agenda {
    return Agenda(
        reminders = this.reminders.map { it.toReminder() },
        // TODO: add task
        // TODO: add event
    )
}
