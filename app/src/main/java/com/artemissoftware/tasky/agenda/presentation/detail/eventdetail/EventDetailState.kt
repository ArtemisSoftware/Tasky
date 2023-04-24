package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail

import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.tasky.agenda.composables.VisitorOptionType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog.AttendeeDialogState
import com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models.Visitor
import java.time.LocalDateTime

data class EventDetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val isEditingNotification: Boolean = false,
    val isEventCreator: Boolean = true,
    val visitorOption: VisitorOptionType = VisitorOptionType.ALL,
    val notGoingVisitors: List<Visitor> = emptyList(),
    val goingVisitors: List<Visitor> = emptyList(),
    val agendaItem: AgendaItem.Event = AgendaItem.Event(),
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now().plusMinutes(30L),
    val title: String = "",
    val pictures: List<Picture> = emptyList(),
    val deletedPictures: List<String> = emptyList(),
    val description: String = "",
    val notification: NotificationType = NotificationType.defaultNotification(),
    val attendeeDialogState: AttendeeDialogState = AttendeeDialogState(),
    val attendees: List<Attendee> = emptyList(),
    val hostId: String = "",
    val hostName: String = "",
    val userId: String = "",
    val creator: Visitor? = null,
    val isGoing: Boolean = true,
) {

//    fun getGoingVisitors(): List<Visitor> {
//        val list = mutableListOf<Visitor>()
//        creator?.let { list.add(it) }
//        list.addAll(attendees.filter { it.isGoing }.map { attendee -> Visitor(attendee = attendee, isEventCreator = isEventCreator(attendee.id)) })
//        list.sortBy { !it.isEventCreator }
//        return list
//    }

    private fun isEventCreator(attendeeId: String) = (attendeeId == hostId)

    fun isEditionOccurring() = isEditing || isEditingNotification
}
