package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Notification
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class SaveReminderUseCase constructor(
    private val agendaRepository: AgendaRepository
){

    suspend operator fun invoke(
        id: String?,
        title: String,
        description: String,
        date: LocalDate,
        time: LocalTime,
        notification: Notification
    ) {

        val isUpdate = id != null
        val reminderTime = LocalDateTime.of(date, time)

        val reminder = AgendaItem.Reminder(
            id =  id ?: UUID.randomUUID().toString(),
            title = title,
            description = description,
            remindAt = reminderTime.minusMinutes(notification.minutesBefore),
            time = reminderTime
        )

        agendaRepository.register(reminder = reminder, isUpdate = isUpdate)
        val result = agendaRepository.sync(reminder = reminder, isUpdate = isUpdate)

        when(result){
            is ApiNetworkResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is ApiNetworkResponse.Success -> {
                agendaRepository.updateSyncState(reminder.id)
            }
        }
    }
}