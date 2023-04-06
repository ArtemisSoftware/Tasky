package com.artemissoftware.tasky.agenda.presentation.detail.reminderdetail

import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import java.time.LocalDateTime

data class ReminderDetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val agendaItem: AgendaItem.Reminder = AgendaItem.Reminder(),
    val startDate: LocalDateTime = LocalDateTime.now(),
    val title: String = "",
    val description: String = "",
    val notification: NotificationType = NotificationType.defaultNotification(),
)
