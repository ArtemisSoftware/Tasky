package com.artemissoftware.core.presentation.events

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class TaskyUiEventViewModel <E: TaskyEvents> : ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    open fun onTriggerEvent(event: E) {}
}