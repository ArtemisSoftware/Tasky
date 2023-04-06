package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.domain.models.agenda.NotificationType.Companion.defaultNotification
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import java.time.LocalDateTime

data class DetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val agendaItemType: AgendaItemType? = null, // TODO: remove when TaskDetailScreen and EventDetailScreen are ready
    val agendaItem: AgendaItem? = null,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val title: String = "",
    val description: String = "",
    val notification: NotificationType = defaultNotification(),
    val specification: DetailSpecification,
)

sealed interface DetailSpecification {

    data class Event( // TODO: complete when EventDetailScreen is ready
        val to: LocalDateTime = LocalDateTime.now(),
    ) : DetailSpecification

    data class Task(
        val isDone: Boolean = false,
    ) : DetailSpecification

    object Reminder : DetailSpecification
}
