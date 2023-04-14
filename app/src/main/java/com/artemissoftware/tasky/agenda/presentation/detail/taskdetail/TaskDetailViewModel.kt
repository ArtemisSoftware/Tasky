package com.artemissoftware.tasky.agenda.presentation.detail.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.usecase.task.GetTaskUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.SaveTaskUseCase
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.agenda.util.NavigationConstants.TASK_ID
import com.artemissoftware.tasky.destinations.EditScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val saveTaskUseCase: SaveTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val savedStateHandle: SavedStateHandle,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(TaskDetailState())
    val state: StateFlow<TaskDetailState> = _state.asStateFlow()

    init {
        loadDetail()
    }

    fun onTriggerEvent(event: DetailEvents) {
        when (event) {
            DetailEvents.ToggleEdition -> { toggleEdition() }
            is DetailEvents.ToggleIsDone -> { toggleIsDone() }
            is DetailEvents.EditDescription -> { editTitleOrDescription(event.description, EditType.Description) }
            is DetailEvents.EditTitle -> { editTitleOrDescription(event.title, EditType.Title) }
            DetailEvents.PopBackStack -> { popBackStack() }
            DetailEvents.Save -> { saveTask() }
            is DetailEvents.UpdateDescription -> { updateDescription(event.description) }
            is DetailEvents.UpdateNotification -> { updateNotification(event.notification) }
            is DetailEvents.UpdateStartDate -> { updateStartDate(event.startDate) }
            is DetailEvents.UpdateStartTime -> { updateStartTime(event.startTime) }
            is DetailEvents.UpdateTitle -> { updateTitle(event.title) }
            else -> Unit
        }
    }

    private fun toggleEdition() = with(_state) {
        update {
            it.copy(
                isEditing = !it.isEditing,
            )
        }
    }

    private fun toggleIsDone() = with(_state) {
        update {
            it.copy(
                isDone = !it.isDone,
            )
        }
    }

    private fun editTitleOrDescription(text: String, editType: EditType) {
        viewModelScope.launch {
            sendUiEvent(UiEvent.Navigate(EditScreenDestination(text, editType).route))
        }
    }

    private fun popBackStack() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.PopBackStack)
        }
    }

    private fun updateDescription(text: String) = with(_state) {
        update {
            it.copy(
                description = text,
            )
        }
    }

    private fun updateTitle(text: String) = with(_state) {
        update {
            it.copy(
                title = text,
            )
        }
    }

    private fun updateNotification(notification: NotificationType) = with(_state) {
        update {
            it.copy(
                notification = notification,
            )
        }
    }

    private fun updateStartDate(startDate: LocalDate) = with(_state) {
        update {
            it.copy(
                startDate = it.startDate.with(startDate),
            )
        }
    }

    private fun updateStartTime(startTime: LocalTime) = with(_state) {
        val result = value.startDate
            .withHour(startTime.hour)
            .withMinute(startTime.minute)

        update {
            it.copy(
                startDate = result,
            )
        }
    }

    private fun loadDetail() {
        savedStateHandle.get<String>(TASK_ID)?.let { taskId ->
            viewModelScope.launch {
                val result = getTaskUseCase(taskId)
                result?.let { item ->
                    _state.update {
                        it.copy(
                            agendaItem = item,
                            notification = NotificationType.getNotification(remindAt = item.remindAt, startDate = item.starDate),
                            startDate = item.time,
                            title = item.title,
                            description = item.description ?: "",
                            isDone = item.isDone,
                        )
                    }
                }
            }
        }
    }

    private fun saveTask() = with(_state.value) {
        val item = AgendaItem.Task(
            id = agendaItem.id,
            title = title,
            description = description,
            remindAt = NotificationType.remindAt(time = startDate, notificationType = notification),
            time = startDate,
            isDone = isDone,
            syncState = getSyncType(agendaItem),
        )

        viewModelScope.launch {
            saveTaskUseCase(item)
            popBackStack()
        }
    }

    private fun getSyncType(agendaItem: AgendaItem.Task): SyncType {
        return savedStateHandle.get<String>(TASK_ID)?.let {
            if (agendaItem.syncState == SyncType.SYNCED) SyncType.UPDATE else agendaItem.syncState
        } ?: SyncType.CREATE
    }
}
