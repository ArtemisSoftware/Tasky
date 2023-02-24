package com.artemissoftware.tasky.base

import androidx.lifecycle.ViewModel
import com.artemissoftware.tasky.base.events.TYEvents
import androidx.lifecycle.viewModelScope
import com.artemissoftware.tasky.base.events.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class TYBaseEventViewModel <E: TYEvents> : ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    open fun onTriggerEvent(event: E) {}

    protected fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}