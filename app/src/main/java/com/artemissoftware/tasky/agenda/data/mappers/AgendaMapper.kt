package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.artemissoftware.tasky.agenda.domain.models.Agenda

fun AgendaResponseDto.toAgenda(): Agenda {
    return Agenda(
        items = this.reminders.map { it.toReminder() } + this.tasks.map { it.toTask() } + this.events.map { it.toEvent() },
    )
}
