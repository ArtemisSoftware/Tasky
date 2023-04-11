package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.artemissoftware.tasky.agenda.domain.models.Agenda

fun AgendaResponseDto.toAgenda(): Agenda {
    return Agenda(
        reminders = this.reminders.map { it.toReminder() },
        // TODO: add task
        // TODO: add event
    )
}
