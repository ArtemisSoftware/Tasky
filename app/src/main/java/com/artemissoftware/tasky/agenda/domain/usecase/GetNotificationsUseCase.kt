package com.artemissoftware.tasky.agenda.domain.usecase

import com.artemissoftware.tasky.agenda.domain.models.Notification
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val agendaRepository: AgendaRepository,
) {
    suspend operator fun invoke(): List<Notification> =
        agendaRepository.getNotifications()
}
