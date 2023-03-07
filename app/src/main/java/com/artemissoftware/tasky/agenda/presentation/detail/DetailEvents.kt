package com.artemissoftware.tasky.agenda.presentation.detail

import com.artemissoftware.core.presentation.events.TaskyEvents

sealed class DetailEvents: TaskyEvents() {

    object Save : DetailEvents()
    object Edit : DetailEvents()
    object PopBackStack : DetailEvents()
}
