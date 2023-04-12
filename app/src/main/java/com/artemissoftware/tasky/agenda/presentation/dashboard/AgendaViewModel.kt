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
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.DayOfWeek
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.GetAgendaItemsUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.LogOutUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.SyncAgendaUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.DeleteReminderUseCase
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItems
import com.artemissoftware.tasky.destinations.LoginScreenDestination
import com.artemissoftware.tasky.destinations.ReminderDetailScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.IntStream
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAgendaItemsUseCase: GetAgendaItemsUseCase,
    private val syncAgendaUseCase: SyncAgendaUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(AgendaState())
    val state: StateFlow<AgendaState> = _state

    init {
        getMonthDates(selectedDay = _state.value.selectedDayOfTheWeek)
        getUser()
        updateAgenda(date = LocalDate.now())
    }

    fun onTriggerEvent(event: AgendaEvents) {
        when (event) {
            is AgendaEvents.ChangeDate -> {
                changeDate(event.date)
            }
            is AgendaEvents.ChangeWeekDay -> {
                changeWeekDay(event.date)
            }
            is AgendaEvents.CompleteAssignment -> TODO()
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

    private fun updateAgenda(date: LocalDate) {
        getAgendaItems(date = date)
        //syncAgenda(date = date)
    }

    private fun changeDate(date: LocalDate) {
        val selectedDay = _state.value.selectedDayOfTheWeek

        if (selectedDay.year != date.year || selectedDay.month != date.month) {
            getMonthDates(selectedDay = date)
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
                    deleteReminderUseCase.invoke(id = item.itemId)
                }
                is AgendaItem.Task -> TODO()
            }
        }
    }

    private fun goToDetail(item: AgendaItem) {
        viewModelScope.launch {
            when (item) {
                is AgendaItem.Reminder -> {
                    sendUiEvent(UiEvent.Navigate(ReminderDetailScreenDestination(reminderId = item.itemId).route))
                }
                is AgendaItem.Task -> TODO()
            }
        }
    }

    private fun createAgendaItem(detailType: AgendaItems) {
        viewModelScope.launch {
            when (detailType) {
                AgendaItems.EVENT -> TODO()
                AgendaItems.TASK -> TODO()
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

    private fun getMonthDates(selectedDay: LocalDate) {
        val yearMonth = YearMonth.of(selectedDay.year, selectedDay.month)
        val firstOfMonth: LocalDate = yearMonth.atDay(1)
        val firstOfFollowingMonth: LocalDate = yearMonth.plusMonths(1).atDay(1)
        val numOfDaysBetween: Long = ChronoUnit.DAYS.between(firstOfMonth, firstOfFollowingMonth)

        val listOfDates = IntStream.iterate(0) { i -> i + 1 }
            .limit(numOfDaysBetween)
            .mapToObj { i -> firstOfMonth.plusDays(i.toLong()) }
            .collect(Collectors.toList())
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
