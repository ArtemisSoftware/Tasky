package com.artemissoftware.tasky.agenda.presentation.edit

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType

data class EditState(
    val isLoading: Boolean = false,
    val editType: EditType = EditType.Description,
    val agendaItem: AgendaItem? = null,
)
