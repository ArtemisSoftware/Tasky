package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val alarmScheduler: AlarmScheduler,
) {
    suspend operator fun invoke(id: String) {
        val result = reminderRepository.deleteReminderAndSync(id)
        alarmScheduler.cancel(id)

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
