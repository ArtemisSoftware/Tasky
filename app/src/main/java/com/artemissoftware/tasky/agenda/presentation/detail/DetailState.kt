package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

data class DetailState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val agendaItemType: AgendaItemType? = null,
    val agendaItem: AgendaItem? = null
)
