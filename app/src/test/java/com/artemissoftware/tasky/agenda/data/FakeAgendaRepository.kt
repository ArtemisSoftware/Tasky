package com.artemissoftware.tasky.agenda.data

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import com.artemissoftware.tasky.util.FakeData

class FakeAgendaRepository : AgendaRepository{

    private var reminders = mutableListOf(FakeData.reminder)

    override suspend fun getReminder(id: String): AgendaItem.Reminder {
        return reminders[0]
    }

    override suspend fun deleteReminder(id: String) {
        reminders.removeAt(0)
    }

    override suspend fun register(reminder: AgendaItem.Reminder) {
        reminders.add(reminder)
    }
}