package com.artemissoftware.tasky.agenda.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.usecase.GetUserUseCase
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.GetAgendaItemsUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.SyncAgendaUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.DeleteReminderUseCase
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItems
import com.artemissoftware.tasky.destinations.ReminderDetailScreenDestination
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
    private val getUserUseCase: GetUserUseCase,
    private val getAgendaItemsUseCase: GetAgendaItemsUseCase,
    private val syncAgendaUseCase: SyncAgendaUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(AgendaState())
    val state: StateFlow<AgendaState> = _state

    init {
        getUser()
        getAgendaItems(date = LocalDate.now())
        syncAgenda(date = LocalDate.now())
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase().collectLatest { user->
                _state.update {
                    it.copy(
                        userName = user.fullName,
                    )
                }
            }
        }
    }

    fun onTriggerEvent(event: AgendaEvents) {
        when (event) {
            is AgendaEvents.ChangeDate -> TODO()
            is AgendaEvents.ChangeWeekDay -> TODO()
            is AgendaEvents.CompleteAssignment -> TODO()
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
}
