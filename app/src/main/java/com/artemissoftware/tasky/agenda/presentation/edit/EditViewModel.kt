package com.artemissoftware.tasky.agenda.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditRecipient
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.agenda.util.NavigationConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(getState() ?: EditState())
    val state: StateFlow<EditState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun onTriggerEvent(event: EditEvents) {
        when (event) {
            EditEvents.PopBackStack -> { popBackStack() }
            EditEvents.Update -> {
                update()
            }
            is EditEvents.UpdateText -> {
                updateText(event.text)
            }
        }
    }

    private fun updateState() {
        savedStateHandle["state"] = _state.value
    }

    private fun getState() = (savedStateHandle.get<EditState>("state"))

    private fun updateText(text: String) = with(_state) {
        update {
            it.copy(
                text = text,
            )
        }

        updateState()
    }

    private fun popBackStack() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.PopBackStack)
        }
    }

    private fun loadData() = with(_state) {
        val text = savedStateHandle.get<String>(NavigationConstants.TEXT).orEmpty()
        val editType = savedStateHandle.get<EditType>(NavigationConstants.IS_EDITING) ?: EditType.Title

        update {
            it.copy(
                text = text,
                editType = editType,
            )
        }

        updateState()
    }

    private fun update() {
        viewModelScope.launch {
            with(_state.value) {
                sendUiEvent(UiEvent.PopBackStackWithArguments(arguments = EditRecipient(editType = editType, text = text)))
            }
        }
    }
}
