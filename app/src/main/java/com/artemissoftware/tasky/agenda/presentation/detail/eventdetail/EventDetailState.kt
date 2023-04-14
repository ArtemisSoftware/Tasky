package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail

import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.tasky.agenda.composables.VisitorOptionType
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.models.Photo
import com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog.AttendeeDialogState
import com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models.Visitor
import java.time.LocalDateTime

data class EventDetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val eventCreatorId: String = "",
    val visitorOption: VisitorOptionType = VisitorOptionType.ALL,
    // val agendaItem: AgendaItem.Event = AgendaItem.Event(), // TODO add when AgendaItem.Event() is ready
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now().plusMinutes(30L),
    val title: String = "",
    val photos: List<Photo> = emptyList(),
    val description: String = "",
    val notification: NotificationType = NotificationType.defaultNotification(),
    val attendeeDialogState: AttendeeDialogState = AttendeeDialogState(),
    val attendees: List<Attendee> = emptyList(),
) {

    fun getGoingVisitors(): List<Visitor> {
        return attendees.filter { it.isGoing }.map { attendee -> Visitor(attendee = attendee, isEventCreator = isEventCreator(attendee.id)) }
    }

    fun getNotGoingVisitors(): List<Visitor> {
        return attendees.filter { !it.isGoing }.map { attendee -> Visitor(attendee = attendee, isEventCreator = isEventCreator(attendee.id)) }
    }

    private fun isEventCreator(attendeeId: String) = (attendeeId == eventCreatorId)
}
