package com.artemissoftware.tasky.agenda.domain.usecase.event

import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import com.artemissoftware.tasky.agenda.domain.uploader.EventUploader
import javax.inject.Inject

class SaveEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val eventUploader: EventUploader,
    private val alarmScheduler: AlarmScheduler,
) {
    suspend operator fun invoke(
        event: AgendaItem.Event,
        attendeeLeftEvent: Boolean = false,
    ) {
        eventRepository.saveEventAndSync(event = event)
        if(attendeeLeftEvent){
            alarmScheduler.cancel(id = event.id)
        }
        else {
            alarmScheduler.schedule(item = event)
        }
        val syncType = if (event.syncState == SyncType.SYNCED) SyncType.UPDATE else event.syncState
        eventUploader.upload(event = event, syncType)
    }
}
