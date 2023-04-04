package com.artemissoftware.tasky.agenda.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.GetAgendaItemsUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.SyncAgendaUseCase
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItems
import com.artemissoftware.tasky.destinations.ReminderDetailScreenDestination
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
    private val syncAgendaUseCase: SyncAgendaUseCase
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
            is AgendaEvents.CompleteAssignment -> TODO()
            is AgendaEvents.Delete -> TODO()
            is AgendaEvents.GoToDetail -> {
                goToDetail(event.id, event.detailType)
            }
            AgendaEvents.LogOut -> TODO()
            is AgendaEvents.CreateAgendaItem -> {
                createAgendaItem(event.detailType)
            }
        }
    }

    private fun goToDetail(id: String?, detailType: AgendaItems) {
        viewModelScope.launch {
            when (detailType) {
                AgendaItems.EVENT -> TODO()
                AgendaItems.TASK -> TODO()
                AgendaItems.REMINDER -> {
                    sendUiEvent(UiEvent.Navigate(ReminderDetailScreenDestination(reminderId = id).route))
                }
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
           getAgendaItemsUseCase(date = date).collect{ result ->
               _state.update {
                   it.copy(
                       agendaItems = result,
                   )
               }


           }


        }
    }

    private fun syncAgenda(date: LocalDate) {
        viewModelScope.launch {
            syncAgendaUseCase(date = date)
        }
    }
}
