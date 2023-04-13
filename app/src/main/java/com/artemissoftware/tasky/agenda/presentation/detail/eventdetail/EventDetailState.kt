package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail

import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.VisitorOptionType
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.models.Photo
import com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog.AttendeeDialogState
import com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models.VisitorEvent
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

    fun getVisitors(): List<VisitorEvent> {
        val result = mutableListOf<VisitorEvent>()

        when (visitorOption) {
            VisitorOptionType.ALL -> {
                result.addAll(attendees.map { attendee -> VisitorEvent.Visitor(attendee = attendee, isEventCreator = isEventCreator(attendee.id)) })
            }
            VisitorOptionType.GOING -> {
                result.add(VisitorEvent.Title(textId = R.string.going))
                result.addAll(attendees.filter { it.isGoing }.map { attendee -> VisitorEvent.Visitor(attendee = attendee, isEventCreator = isEventCreator(attendee.id)) })
            }
            VisitorOptionType.NOT_GOING -> {
                result.add(VisitorEvent.Title(textId = R.string.not_going))
                result.addAll(attendees.filter { !it.isGoing }.map { attendee -> VisitorEvent.Visitor(attendee = attendee, isEventCreator = isEventCreator(attendee.id)) })
            }
        }
        return result
    }

    private fun isEventCreator(attendeeId: String) = attendeeId == eventCreatorId
}
