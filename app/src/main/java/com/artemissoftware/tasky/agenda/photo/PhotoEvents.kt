package com.artemissoftware.tasky.agenda.photo

import com.artemissoftware.core.presentation.events.TaskyEvents

sealed class PhotoEvents: TaskyEvents() {
    object PopBackStack: PhotoEvents()
    data class DeletePhoto(val photoId: String): PhotoEvents()
}
