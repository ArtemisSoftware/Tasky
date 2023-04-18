package com.artemissoftware.tasky.agenda.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.GetAgendaItemsUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.SyncAgendaUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.event.DeleteEventUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.DeleteReminderUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.CompleteTaskUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.DeleteTaskUseCase
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItems
import com.artemissoftware.tasky.destinations.EventDetailScreenDestination
import com.artemissoftware.tasky.destinations.ReminderDetailScreenDestination
import com.artemissoftware.tasky.destinations.TaskDetailScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getAgendaItemsUseCase: GetAgendaItemsUseCase,
    private val syncAgendaUseCase: SyncAgendaUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(AgendaState())
    val state: StateFlow<AgendaState> = _state

    init {
        getAgendaItems(date = LocalDate.now())
        syncAgenda(date = LocalDate.now())
    }

    fun onTriggerEvent(event: AgendaEvents) {
        when (event) {
            is AgendaEvents.ChangeDate -> TODO()
            is AgendaEvents.ChangeWeekDay -> TODO()
            is AgendaEvents.CompleteAssignment -> {
                completeAssignment(event.item)
            }
            is AgendaEvents.Delete -> {
                deleteItem(item = event.item)
            }
            is AgendaEvents.GoToDetail -> {
                goToDetail(item = event.item)
            }
            AgendaEvents.LogOut -> TODO()
            is AgendaEvents.CreateAgendaItem -> {
                createAgendaItem(event.detailType)
            }
        }
    }

    private fun completeAssignment(item: AgendaItem.Task) {
        viewModelScope.launch {
            completeTaskUseCase.invoke(task = item)
        }
    }

    private fun deleteItem(item: AgendaItem) {
        viewModelScope.launch {
            when (item) {
                is AgendaItem.Reminder -> {
                    deleteReminderUseCase(id = item.itemId)
                }
                is AgendaItem.Task -> {
                    deleteTaskUseCase(id = item.itemId)
                }
                is AgendaItem.Event -> {
                    deleteEventUseCase(id = item.itemId)
                }
            }
        }
    }

    private fun goToDetail(item: AgendaItem) {
        viewModelScope.launch {
            when (item) {
                is AgendaItem.Reminder -> {
                    sendUiEvent(UiEvent.Navigate(ReminderDetailScreenDestination(reminderId = item.itemId).route))
                }
                is AgendaItem.Task -> {
                    sendUiEvent(UiEvent.Navigate(TaskDetailScreenDestination(taskId = item.itemId).route))
                }
                is AgendaItem.Event -> {
                    (UiEvent.Navigate(EventDetailScreenDestination(eventId = item.itemId, userId = _state.value.userId).route))
                }
            }
        }
    }

    private fun createAgendaItem(detailType: AgendaItems) {
        viewModelScope.launch {
            when (detailType) {
                AgendaItems.EVENT -> {
                    sendUiEvent(UiEvent.Navigate(EventDetailScreenDestination(userId = _state.value.userId).route))
                }
                AgendaItems.TASK -> {
                    sendUiEvent(UiEvent.Navigate(TaskDetailScreenDestination().route))
                }
                AgendaItems.REMINDER -> {
                    sendUiEvent(UiEvent.Navigate(ReminderDetailScreenDestination().route))
                }
            }
        }
    }

    private fun getAgendaItems(date: LocalDate) {
        viewModelScope.launch {
            getAgendaItemsUseCase(date = date).collect { result ->
                _state.update {
                    it.copy(
                        agendaItems = result,
                    )
                }
            }
        }
    }

    private fun syncAgenda(date: LocalDate) {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            syncAgendaUseCase(date = date)

            _state.update {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }
}
