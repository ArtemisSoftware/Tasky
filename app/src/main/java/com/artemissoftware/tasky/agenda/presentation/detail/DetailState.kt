package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Notification
import java.time.LocalDateTime

data class DetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val agendaItemType: AgendaItemType? = null, // TODO: remove when TaskDetailScreen and EventDetailScreen are ready
    val agendaItem: AgendaItem? = null,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val title: String = "",
    val description: String = "",
    val notification: Notification? = null,
    val specification: DetailSpecification,
)

sealed interface DetailSpecification {

    data class Event( // TODO: complete when EventDetailScreen is ready
        val to: LocalDateTime = LocalDateTime.now(),
        val endDate: LocalDateTime = LocalDateTime.now(),
    ) : DetailSpecification

    data class Task( // TODO: complete when TaskDetailScreen is ready
        val isDone: Boolean = false,
    ) : DetailSpecification

    object Reminder : DetailSpecification
}
