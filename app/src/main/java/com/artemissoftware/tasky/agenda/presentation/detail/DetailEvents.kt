package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.core.presentation.events.TaskyEvents
import com.artemissoftware.tasky.agenda.domain.models.Notification
import java.time.LocalDate
import java.time.LocalTime

sealed class DetailEvents : TaskyEvents() {

    object Save : DetailEvents()
    object Edit : DetailEvents()
    object PopBackStack : DetailEvents()

    data class UpdateStartDate(val startDate: LocalDate) : DetailEvents()
    data class UpdateEndDate(val endDate: LocalDate) : DetailEvents()
    data class UpdateStartTime(val startTime: LocalTime) : DetailEvents()
    data class UpdateEndTime(val endTime: LocalTime) : DetailEvents()
    data class UpdateNotification(val notification: Notification) : DetailEvents()
    data class EditTitle(val title: String) : DetailEvents()

    data class EditDescription(val description: String) : DetailEvents()
}
