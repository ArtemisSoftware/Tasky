package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Notification
import java.time.LocalDateTime

data class DetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val agendaItemType: AgendaItemType? = null,
    val agendaItem: AgendaItem? = null,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val title: String = "",
    val description: String = "",
    val notification: Notification? = null,
)
