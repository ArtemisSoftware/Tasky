package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class GetAgendaToUpdateAlarmsUseCase @Inject constructor(
    private val agendaRepository: AgendaRepository,
) {
    suspend operator fun invoke(): List<AgendaItem> {
        val date = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return agendaRepository.getAgendaToUpdateAlarms(currentDate = date)
    }
}
