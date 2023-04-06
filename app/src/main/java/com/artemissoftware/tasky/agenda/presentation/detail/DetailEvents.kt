package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.events.TaskyEvents
import java.time.LocalDate
import java.time.LocalTime

sealed class DetailEvents : TaskyEvents() {

    object Save : DetailEvents()
    object ToggleEdition : DetailEvents()

    object ToggleIsDone : DetailEvents()
    object PopBackStack : DetailEvents()

    data class LoadDetail(val id: String? = null) : DetailEvents()

    data class UpdateStartDate(val startDate: LocalDate) : DetailEvents()
    data class UpdateEndDate(val endDate: LocalDate) : DetailEvents()
    data class UpdateStartTime(val startTime: LocalTime) : DetailEvents()
    data class UpdateEndTime(val endTime: LocalTime) : DetailEvents()
    data class UpdateNotification(val notification: NotificationType) : DetailEvents()
    data class EditTitle(val title: String) : DetailEvents()
    data class EditDescription(val description: String) : DetailEvents()
    data class UpdateTitle(val title: String) : DetailEvents()
    data class UpdateDescription(val description: String) : DetailEvents()
}
