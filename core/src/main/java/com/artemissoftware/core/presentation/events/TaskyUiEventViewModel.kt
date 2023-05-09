package com.artemissoftware.core.presentation.events

import androidx.lifecycle.ViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class TaskyUiEventViewModel : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    protected suspend fun sendUiEvent(uiEvent: UiEvent) {
        _uiEvent.send(uiEvent)
    }
}
