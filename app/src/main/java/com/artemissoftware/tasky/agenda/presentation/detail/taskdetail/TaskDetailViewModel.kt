package com.artemissoftware.tasky.agenda.presentation.detail.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.usecase.task.DeleteTaskUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.GetTaskUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.SaveTaskUseCase
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.EventDetailState
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.agenda.util.NavigationConstants
import com.artemissoftware.tasky.agenda.util.NavigationConstants.TASK_ID
import com.artemissoftware.tasky.destinations.EditScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val saveTaskUseCase: SaveTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val savedStateHandle: SavedStateHandle,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(getState() ?: TaskDetailState())
    val state: StateFlow<TaskDetailState> = _state.asStateFlow()

    init {
        loadDetail()
    }

    private fun updateState(update: (TaskDetailState) -> TaskDetailState) {
        savedStateHandle["state"] = _state.updateAndGet { update(it) }
    }

    private fun getState() = (savedStateHandle.get<TaskDetailState>("state"))?.copy(isLoading = false, isEditing = false)

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
            DetailEvents.Delete -> {
                deleteTask()
            }
            else -> Unit
        }
    }

    private fun updateDescription(text: String) {
        updateState {
            it.copy(
                description = text,
            )
        }
    }

    private fun updateTitle(text: String) {
        updateState {
            it.copy(
                title = text,
            )
        }
    }

    private fun updateNotification(notification: NotificationType) {
        updateState {
            it.copy(
                notification = notification,
            )
        }
    }

    private fun updateStartDate(startDate: LocalDate) {
        updateState {
            it.copy(
                startDate = it.startDate.with(startDate),
            )
        }
    }

    private fun updateStartTime(startTime: LocalTime) = with(_state) {
        val result = value.startDate
            .withHour(startTime.hour)
            .withMinute(startTime.minute)

        updateState {
            it.copy(
                startDate = result,
            )
        }
    }

    private fun toggleIsDone() {
        updateState {
            it.copy(
                isDone = !it.isDone,
            )
        }
    }

    private fun deleteTask() {
        savedStateHandle.get<String>(TASK_ID)?.let { taskId ->
            viewModelScope.launch {
                deleteTaskUseCase(id = taskId)
                popBackStack()
            }
        }
    }

    private fun toggleEdition() = with(_state) {
        update {
            it.copy(
                isEditing = !it.isEditing,
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

    private fun loadDetail() = with(_state) {
        val isEditing = savedStateHandle.get<Boolean>(NavigationConstants.IS_EDITING) ?: false
        savedStateHandle.get<String>(TASK_ID)?.let { taskId ->
            viewModelScope.launch {
                val result = getTaskUseCase(taskId)
                result?.let { item ->
                    update {
                        it.copy(
                            isEditing = isEditing,
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
