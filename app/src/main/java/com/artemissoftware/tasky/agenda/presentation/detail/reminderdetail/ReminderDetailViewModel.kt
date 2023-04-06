package com.artemissoftware.tasky.agenda.presentation.detail.reminderdetail

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.GetReminderUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.SaveReminderUseCase
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
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
class ReminderDetailViewModel @Inject constructor(
    private val saveReminderUseCase: SaveReminderUseCase,
    private val getReminderUseCase: GetReminderUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(ReminderDetailState())
    val state: StateFlow<ReminderDetailState> = _state.asStateFlow()

    fun onTriggerEvent(event: DetailEvents) {
        when (event) {
            DetailEvents.ToggleEdition -> {
                toggleEdition()
            }
            is DetailEvents.EditDescription -> {
                editTitleOrDescription(event.description, EditType.Description)
            }
            is DetailEvents.EditTitle -> {
                editTitleOrDescription(event.title, EditType.Title)
            }
            DetailEvents.PopBackStack -> { popBackStack() }
            DetailEvents.Save -> { saveReminder() }
            is DetailEvents.UpdateNotification -> { updateNotification(event.notification) }
            is DetailEvents.UpdateStartDate -> { updateStartDate(event.startDate) }
            is DetailEvents.UpdateStartTime -> { updateStartTime(event.startTime) }
            is DetailEvents.LoadDetail -> {
                loadDetail(event.id)
            }
            is DetailEvents.UpdateDescription -> {
                updateDescription(event.description)
            }
            is DetailEvents.UpdateTitle -> {
                updateTitle(event.title)
            }
            else -> Unit
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

    private fun toggleEdition() {
        _state.update {
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

    private fun loadDetail(id: String?) {
        id?.let { reminderId ->
            viewModelScope.launch {
                val result = getReminderUseCase(reminderId)
                result?.let { item ->
                    _state.update {
                        it.copy(
                            agendaItem = item,
                            notification = NotificationType.getNotification(remindAt = item.remindAt, startDate = item.starDate),
                            startDate = item.time,
                            title = item.title,
                            description = item.description ?: "",
                        )
                    }
                }
            }
        }
    }

    private fun saveReminder() = with(_state.value) {
        agendaItem.title = title
        agendaItem.description = description
        agendaItem.remindAt = NotificationType.remindAt(time = startDate, notificationType = notification)
        agendaItem.time = startDate

        viewModelScope.launch {
            saveReminderUseCase(agendaItem)
            popBackStack()
        }
    }
}
