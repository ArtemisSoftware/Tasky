package com.artemissoftware.tasky.agenda.presentation.edit

import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType

data class EditState(
    val editType: EditType = EditType.Title,
    val text: String = "",
)
