package com.artemissoftware.tasky.agenda.domain.usecase.event

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import javax.inject.Inject

class SaveEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val alarmScheduler: AlarmScheduler,
) {
    suspend operator fun invoke(
        event: AgendaItem.Event,
    ) {
        val result = eventRepository.saveEventAndSync(event = event)
        alarmScheduler.schedule(item = event)

        when (result) {
            is DataResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is DataResponse.Success -> {
                // TODO: should send message to the ui saying the everything went well?
            }
        }
    }
}
