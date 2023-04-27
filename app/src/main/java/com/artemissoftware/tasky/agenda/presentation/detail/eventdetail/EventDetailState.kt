package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail

import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.composables.scaffold.TaskyDialogState
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffoldState
import com.artemissoftware.tasky.agenda.composables.VisitorOptionType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog.AttendeeDialogState
import java.time.LocalDateTime

data class EventDetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val isEventCreator: Boolean = true,
    val visitorOption: VisitorOptionType = VisitorOptionType.ALL,
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
    val isGoing: Boolean = true,
    val taskyScaffoldState: TaskyScaffoldState = TaskyScaffoldState(),
)
