package com.artemissoftware.tasky.agenda.presentation.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.GetReminderUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.SaveReminderUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.GetTaskUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.SaveTaskUseCase
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditItem
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getReminderUseCase: GetReminderUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val saveReminderUseCase: SaveReminderUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(EditState())
    val state: StateFlow<EditState> = _state

    var text by mutableStateOf("")
        private set

    fun onTriggerEvent(event: EditEvents) {
        when (event) {
            EditEvents.PopBackStack -> { popBackStack() }
            EditEvents.Update -> {
                update()
            }
            is EditEvents.LoadData -> {
                loadData(event.editItem)
            }
            is EditEvents.UpdateText -> {
                text = event.text
            }
        }
    }

    private fun popBackStack() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.PopBackStack)
        }
    }

    private fun loadData(editItem: EditItem) {
        _state.update {
            it.copy(
                editType = editItem.editType,
            )
        }
        viewModelScope.launch {
            val result = when (editItem) {
                is EditItem.Event -> TODO()
                is EditItem.Reminder -> getReminderUseCase.invoke(id = editItem.id)
                is EditItem.Task -> getTaskUseCase.invoke(id = editItem.id)
            }

            _state.update {
                it.copy(agendaItem = result)
            }

            text = result?.let {
                if (editItem.editType == EditType.Description) it.description else it.title
            } ?: ""
        }
    }

    private fun update() {
        with(_state.value) {
            agendaItem?.let {
                if (editType is EditType.Description) {
                    it.description = text
                } else {
                    it.title = text
                }

                viewModelScope.launch {
                    when (it) {
                        is AgendaItem.Reminder -> saveReminderUseCase.invoke(reminder = it)
                        is AgendaItem.Task -> saveTaskUseCase.invoke(task = it)
                    }

                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }
}
