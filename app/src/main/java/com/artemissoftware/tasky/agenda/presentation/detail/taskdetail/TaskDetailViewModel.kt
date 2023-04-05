package com.artemissoftware.tasky.agenda.presentation.detail.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.usecase.task.GetTaskUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.SaveTaskUseCase
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.detail.DetailSpecification
import com.artemissoftware.tasky.agenda.presentation.detail.DetailState
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
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

    private val _state = MutableStateFlow(DetailState(specification = DetailSpecification.Task()))
    val state: StateFlow<DetailState> = _state.asStateFlow()

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

    private fun toggleEdition() {
        _state.update {
            it.copy(
                isEditing = !_state.value.isEditing,
            )
        }
    }

    private fun toggleIsDone() {
        val specification = (_state.value.specification as DetailSpecification.Task)
        _state.update {
            it.copy(
                specification = specification.copy(isDone = !specification.isDone),
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

    private fun updateDescription(text: String) {
        _state.update {
            it.copy(
                description = text,
            )
        }
    }

    private fun updateTitle(text: String) {
        _state.update {
            it.copy(
                title = text,
            )
        }
    }

    private fun updateNotification(notification: NotificationType) {
        _state.update {
            it.copy(
                notification = notification,
            )
        }
    }

    private fun updateStartDate(startDate: LocalDate) {
        val result = _state.value.startDate
            .withYear(startDate.year)
            .withMonth(startDate.monthValue)
            .withDayOfMonth(startDate.dayOfMonth)

        _state.update {
            it.copy(
                startDate = result,
            )
        }
    }

    private fun updateStartTime(startTime: LocalTime) {
        val result = _state.value.startDate
            .withHour(startTime.hour)
            .withMinute(startTime.minute)

        _state.update {
            it.copy(
                startDate = result,
            )
        }
    }

    private fun loadDetail() {
        val id = savedStateHandle.get<String>("taskId") // TODO: safer way to get the name of the variable on savedStateHandle? is there a way to say TaskDetailScreenDestination.taskId

        viewModelScope.launch {
            id?.let { taskId ->

                val result = getTaskUseCase(taskId)

                _state.update {
                    result?.let { item ->
                        it.copy(
                            agendaItem = item,
                            notification = NotificationType.getNotification(remindAt = item.remindAt, startDate = item.starDate),
                            startDate = item.time,
                            title = item.title,
                            description = item.description ?: "",
                            specification = DetailSpecification.Task(
                                isDone = item.isDone,
                            ),
                        )
                    } ?: run {
                        it.copy(
                            agendaItem = AgendaItem.Task(),
                        )
                    }
                }
            } ?: run {
                _state.update {
                    it.copy(
                        agendaItem = AgendaItem.Task(),
                    )
                }
            }
        }
    }

    private fun saveTask() {
        (_state.value.agendaItem as? AgendaItem.Task)?.let { item ->

            with(_state.value) {
                item.itemTitle = title
                item.itemDescription = description
                item.itemRemindAt = NotificationType.remindAt(time = startDate, notificationType = notification)
                item.starDate = startDate
                item.isDone = (specification as DetailSpecification.Task).isDone
            }

            viewModelScope.launch {
                saveTaskUseCase(item)
                popBackStack()
            }
        }
    }
}
