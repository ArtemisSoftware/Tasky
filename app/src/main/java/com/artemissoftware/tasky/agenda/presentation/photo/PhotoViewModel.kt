package com.artemissoftware.tasky.agenda.presentation.photo

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.presentation.edit.models.PictureRecipient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor() : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(PhotoState())
    val state: StateFlow<PhotoState> = _state.asStateFlow()

    fun onTriggerEvent(event: PhotoEvents) {
        when (event) {
            is PhotoEvents.DeletePhoto -> { deletePhoto(photoId = event.photoId) }
            PhotoEvents.PopBackStack -> { popBackStack() }
        }
    }

    private fun deletePhoto(photoId: String) {
        viewModelScope.launch {
            sendUiEvent(UiEvent.PopBackStackWithArguments(arguments = PictureRecipient(pictureId = photoId)))
        }
    }

    private fun popBackStack() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.PopBackStack)
        }
    }
}
