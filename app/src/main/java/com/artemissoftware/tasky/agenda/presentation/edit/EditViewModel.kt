package com.artemissoftware.tasky.agenda.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.presentation.detail.reminderdetail.ReminderDetailState
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditRecipient
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.agenda.util.NavigationConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private fun updateState(update: (EditState) -> EditState) {
        savedStateHandle["state"] = _state.updateAndGet { update(it) }
    }

    private fun getState() = (savedStateHandle.get<EditState>("state"))

    private fun updateText(text: String) {
        updateState {
            it.copy(
                text = text,
            )
        }
    }

    private fun popBackStack() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.PopBackStack)
        }
    }

    private fun loadData() = with(_state) {
        val text = savedStateHandle.get<String>(NavigationConstants.TEXT).orEmpty()
        val editType = savedStateHandle.get<EditType>(NavigationConstants.EDIT_TYPE) ?: EditType.Title

        updateState {
            it.copy(
                text = text,
                editType = editType,
            )
        }
    }

    private fun update() {
        viewModelScope.launch {
            with(_state.value) {
                sendUiEvent(UiEvent.PopBackStackWithArguments(arguments = EditRecipient(editType = editType, text = text)))
            }
        }
    }
}
