package com.artemissoftware.tasky.agenda.presentation.edit

import com.artemissoftware.core.presentation.events.TaskyEvents
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditItem

sealed class EditEvents : TaskyEvents() {

    object Update : EditEvents()

    object PopBackStack : EditEvents()

    data class LoadData(val editItem: EditItem) : EditEvents()

    data class UpdateText(val text: String) : EditEvents()
}
