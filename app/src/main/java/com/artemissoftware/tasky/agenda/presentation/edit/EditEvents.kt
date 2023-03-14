package com.artemissoftware.tasky.agenda.presentation.edit

import com.artemissoftware.core.presentation.events.TaskyEvents
import com.artemissoftware.tasky.authentication.presentation.register.RegisterEvents

sealed class EditEvents: TaskyEvents() {

    object Save : EditEvents()

    object PopBackStack : EditEvents()
}
