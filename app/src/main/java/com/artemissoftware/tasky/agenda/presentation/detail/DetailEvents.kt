package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.events.TaskyEvents
import com.artemissoftware.tasky.agenda.composables.VisitorOptionType
import java.time.LocalDate
import java.time.LocalTime

sealed class DetailEvents : TaskyEvents() {

    object Save : DetailEvents()
    object ToggleEdition : DetailEvents()

    object ToggleIsDone : DetailEvents()
    object PopBackStack : DetailEvents()

    object OpenAttendeeDialog : DetailEvents()
    object CloseAttendeeDialog : DetailEvents()

    data class UpdateStartDate(val startDate: LocalDate) : DetailEvents()
    data class UpdateEndDate(val endDate: LocalDate) : DetailEvents()
    data class UpdateStartTime(val startTime: LocalTime) : DetailEvents()
    data class UpdateEndTime(val endTime: LocalTime) : DetailEvents()
    data class UpdateNotification(val notification: NotificationType) : DetailEvents()
    data class EditTitle(val title: String) : DetailEvents()
    data class EditDescription(val description: String) : DetailEvents()
    data class UpdateTitle(val title: String) : DetailEvents()
    data class UpdateDescription(val description: String) : DetailEvents()

    data class UpdateAttendeeEmail(val email: String) : DetailEvents()
    object AddAttendee : DetailEvents()

    data class ViewVisitors(val visitorOptionType: VisitorOptionType) : DetailEvents()

    data class DeleteVisitor(val attendeeId: String) : DetailEvents()
}
