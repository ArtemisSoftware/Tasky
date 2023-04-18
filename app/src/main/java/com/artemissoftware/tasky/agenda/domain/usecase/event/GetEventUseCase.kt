package com.artemissoftware.tasky.agenda.domain.usecase.event

import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import javax.inject.Inject

class GetEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(eventId: String) = eventRepository.getEvent(id = eventId)
}
