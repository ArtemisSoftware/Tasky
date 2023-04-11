package com.artemissoftware.tasky.agenda.domain.models

data class Agenda(
    val reminders: List<AgendaItem.Reminder>
)
