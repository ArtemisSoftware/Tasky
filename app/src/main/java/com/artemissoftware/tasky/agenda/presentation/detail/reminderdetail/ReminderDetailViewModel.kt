package com.artemissoftware.tasky.agenda.presentation.detail.reminderdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.DeleteReminderUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.GetReminderUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.reminder.SaveReminderUseCase
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.agenda.util.NavigationConstants.IS_EDITING
import com.artemissoftware.tasky.agenda.util.NavigationConstants.REMINDER_ID
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
    private val deleteReminderUseCase: DeleteReminderUseCase,
    private val savedStateHandle: SavedStateHandle,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(getState() ?: ReminderDetailState())
    val state: StateFlow<ReminderDetailState> = _state.asStateFlow()

    init {
        loadDetail()
    }

    private fun updateState(reminderDetailState: ReminderDetailState) {
        savedStateHandle["state"] = reminderDetailState
    }

    private fun getState() = (savedStateHandle.get<ReminderDetailState>("state"))?.copy(isLoading = false)

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
            is DetailEvents.UpdateDescription -> {
                updateDescription(event.description)
            }
            is DetailEvents.UpdateTitle -> {
                updateTitle(event.title)
            }
            DetailEvents.Delete -> {
                deleteReminder()
            }
            else -> Unit
        }
    }

    private fun deleteReminder() {
        savedStateHandle.get<String>(REMINDER_ID)?.let { reminderId ->
            viewModelScope.launch {
                deleteReminderUseCase(id = reminderId)
                popBackStack()
            }
        }
    }

    private fun updateDescription(text: String) = with(_state) {
        update {
            it.copy(
                description = text,
            )
        }
        updateState(_state.value)
    }

    private fun updateTitle(text: String) = with(_state) {
        update {
            it.copy(
                title = text,
            )
        }
        updateState(_state.value)
    }

    private fun updateNotification(notification: NotificationType) = with(_state) {
        update {
            it.copy(
                notification = notification,
            )
        }
        updateState(_state.value)
    }

    private fun updateStartDate(startDate: LocalDate) = with(_state) {
        update {
            it.copy(
                startDate = it.startDate.with(startDate),
            )
        }
        updateState(_state.value)
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
        updateState(_state.value)
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
        val isEditing = savedStateHandle.get<Boolean>(IS_EDITING) ?: false
        savedStateHandle.get<String>(REMINDER_ID)?.let { reminderId ->
            viewModelScope.launch {
                val result = getReminderUseCase(reminderId)
                result?.let { item ->
                    update {
                        it.copy(
                            isEditing = isEditing,
                            agendaItem = item,
                            notification = NotificationType.getNotification(remindAt = item.remindAt, startDate = item.starDate),
                            startDate = item.time,
                            title = item.title,
                            description = item.description ?: "",
                        )
                    }
                }
            }
        } ?: kotlin.run { updateState(_state.value) }
    }

    private fun saveReminder() = with(_state.value) {
        val item = AgendaItem.Reminder(
            id = agendaItem.id,
            title = title,
            description = description,
            remindAt = NotificationType.remindAt(time = startDate, notificationType = notification),
            time = startDate,
            syncState = getSyncType(agendaItem),
        )

        viewModelScope.launch {
            saveReminderUseCase(item)
            popBackStack()
        }
    }

    private fun getSyncType(agendaItem: AgendaItem.Reminder): SyncType {
        return savedStateHandle.get<String>(REMINDER_ID)?.let {
            if (agendaItem.syncState == SyncType.SYNCED) SyncType.UPDATE else agendaItem.syncState
        } ?: SyncType.CREATE
    }
}
