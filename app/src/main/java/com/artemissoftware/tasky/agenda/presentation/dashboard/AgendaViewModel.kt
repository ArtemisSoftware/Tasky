package com.artemissoftware.tasky.agenda.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.usecase.GetUserUseCase
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.core.presentation.mappers.toUiText
import com.artemissoftware.core.util.UiText
import com.artemissoftware.core.util.extensions.nextDays
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.DayOfWeek
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.GetAgendaItemsUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.LogOutUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.SyncAgendaUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.event.DeleteEventUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.DeleteReminderUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.CompleteTaskUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.task.DeleteTaskUseCase
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItems
import com.artemissoftware.tasky.destinations.EventDetailScreenDestination
import com.artemissoftware.tasky.destinations.LoginScreenDestination
import com.artemissoftware.tasky.destinations.ReminderDetailScreenDestination
import com.artemissoftware.tasky.destinations.TaskDetailScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAgendaItemsUseCase: GetAgendaItemsUseCase,
    private val syncAgendaUseCase: SyncAgendaUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(AgendaState())
    val state: StateFlow<AgendaState> = _state

    init {
        updateDaysOfTheWeek(selectedDay = _state.value.selectedDayOfTheWeek)
        getUser()
        getAgendaItems(date = LocalDate.now())
        syncAgenda(date = LocalDate.now())
    }

    fun onTriggerEvent(event: AgendaEvents) {
        when (event) {
            is AgendaEvents.ChangeDate -> {
                changeDate(event.date)
            }
            is AgendaEvents.ChangeWeekDay -> {
                changeWeekDay(event.date)
            }
            is AgendaEvents.CompleteAssignment -> {
                completeAssignment(event.item)
            }
            is AgendaEvents.Delete -> {
                deleteItem(item = event.item)
            }
            is AgendaEvents.GoToDetail -> {
                goToDetail(item = event.item)
            }
            AgendaEvents.LogOut -> {
                logout()
            }
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

    private fun updateAgenda(date: LocalDate) {
        getAgendaItems(date = date)
        syncAgenda(date = date)
    }

    private fun changeDate(date: LocalDate) {
        if (!_state.value.daysOfTheWeek.any { it.date.isEqual(date) }) {
            updateDaysOfTheWeek(date)
        }

        _state.update {
            it.copy(
                selectedDayOfTheWeek = date,
            )
        }
        updateAgenda(date = date)
    }
    private fun changeWeekDay(date: LocalDate) {
        _state.update {
            it.copy(
                selectedDayOfTheWeek = date,
            )
        }
        updateAgenda(date = date)
    }

    private fun logout() {
        viewModelScope.launch {
            val result = logOutUseCase.invoke()

            when (result) {
                is Resource.Error -> {
                    result.exception?.let {
                        sendUiEvent(UiEvent.ShowDialog(getDialogData(ex = it, reloadEvent = { logout() })))
                    }
                }
                is Resource.Success -> {
                    sendUiEvent(UiEvent.NavigateAndPopCurrent(LoginScreenDestination.route))
                }
                is Resource.Loading -> Unit
            }
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
                    sendUiEvent(UiEvent.Navigate(EventDetailScreenDestination(eventId = item.itemId, userId = _state.value.userId, userName = _state.value.userName).route))
                }
            }
        }
    }

    private fun createAgendaItem(detailType: AgendaItems) {
        viewModelScope.launch {
            when (detailType) {
                AgendaItems.EVENT -> {
                    sendUiEvent(UiEvent.Navigate(EventDetailScreenDestination(userId = _state.value.userId, userName = _state.value.userName).route))
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

    private fun syncAgenda(date: LocalDate) = with(_state) {
        update {
            it.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            syncAgendaUseCase(date = date, loggedInUserId = value.userId)

            update {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }

    private fun updateDaysOfTheWeek(selectedDay: LocalDate) {
        val listOfDates = selectedDay
            .nextDays(numberOfNextDays = 5)
            .map {
                DayOfWeek(date = it)
            }

        _state.update {
            it.copy(
                daysOfTheWeek = listOfDates,
            )
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase().collectLatest { user ->
                _state.update {
                    it.copy(
                        userName = user.fullName,
                    )
                }
            }
        }
    }

    private fun getDialogData(ex: ValidationException, reloadEvent: () -> Unit): TaskyDialogType {
        return TaskyDialogType.Error(
            title = UiText.StringResource(R.string.log_out),
            description = ex.toUiText(),
            dialogOptions = TaskyDialogOptions.DoubleOption(
                confirmationText = UiText.StringResource(R.string.retry),
                confirmation = {
                    reloadEvent.invoke()
                },
                cancelText = UiText.StringResource(com.artemissoftware.core.R.string.cancel),
            ),
        )
    }
}
