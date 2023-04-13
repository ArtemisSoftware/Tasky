package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail

import android.provider.CalendarContract.Instances.EVENT_ID
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.AgendaException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.core.presentation.mappers.toUiText
import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.VisitorOptionType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.usecase.attendee.GetAttendeeUseCase
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog.AttendeeDialogState
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
import com.artemissoftware.core.R as CoreR

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val getAttendeeUseCase: GetAttendeeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(EventDetailState())
    val state: StateFlow<EventDetailState> = _state.asStateFlow()

    init {
        loadDetail()
    }

    fun onTriggerEvent(event: DetailEvents) {
        when (event) {
            DetailEvents.AddAttendee -> {
                addAttendee()
            }
            DetailEvents.OpenAttendeeDialog -> {
                openAttendeeDialog()
            }
            DetailEvents.CloseAttendeeDialog -> {
                closeAttendeeDialog()
            }
            is DetailEvents.EditDescription -> {
                editTitleOrDescription(event.description, EditType.Description)
            }
            is DetailEvents.EditTitle -> {
                editTitleOrDescription(event.title, EditType.Title)
            }
            DetailEvents.PopBackStack -> { popBackStack() }
            DetailEvents.Save -> TODO()
            DetailEvents.ToggleEdition -> {
                toggleEdition()
            }
            is DetailEvents.UpdateAttendeeEmail -> {
                updateAttendeeEmail(event.email)
            }
            is DetailEvents.UpdateDescription -> {
                updateDescription(event.description)
            }
            is DetailEvents.UpdateEndDate -> { updateEndDate(event.endDate) }
            is DetailEvents.UpdateEndTime -> { updateEndTime(event.endTime) }
            is DetailEvents.UpdateNotification -> { updateNotification(event.notification) }
            is DetailEvents.UpdateStartDate -> { updateStartDate(event.startDate) }
            is DetailEvents.UpdateStartTime -> { updateStartTime(event.startTime) }
            is DetailEvents.UpdateTitle -> {
                updateTitle(event.title)
            }
            is DetailEvents.ViewVisitors -> {
                updateVisitorsSelection(event.visitorOptionType)
            }
            else -> Unit
        }
    }

    private fun addAttendee() {
        viewModelScope.launch {
            val result = getAttendeeUseCase(email = _state.value.attendeeDialogState.email)

            when (result) {
                is Resource.Error -> {
                    result.exception?.let { ex ->

                        when (ex) {
                            AgendaException.AttendeeDoesNotExist -> {
                                _state.update {
                                    it.copy(
                                        attendeeDialogState = AttendeeDialogState(errorMessage = ex.toUiText()),
                                    )
                                }
                            }
                            else -> {
                                closeAttendeeDialog()
                                result.exception?.let {
                                    sendUiEvent(UiEvent.ShowDialog(getDialogData(ex = it)))
                                }
                            }
                        }
                    }
                }
                is Resource.Success -> TODO()
                is Resource.Loading -> Unit
            }
        }
    }

    private fun updateVisitorsSelection(visitorOptionType: VisitorOptionType) {
        _state.update {
            it.copy(
                visitorOption = visitorOptionType,
            )
        }
    }

    private fun openAttendeeDialog() {
        _state.update {
            it.copy(
                attendeeDialogState = AttendeeDialogState(showDialog = true),
            )
        }
    }

    private fun closeAttendeeDialog() {
        _state.update {
            it.copy(
                attendeeDialogState = AttendeeDialogState(),
            )
        }
    }

    private fun updateAttendeeEmail(email: String) {
        _state.update {
            it.copy(
                attendeeDialogState = it.attendeeDialogState.copy(email = email),
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

    private fun updateEndDate(endDate: LocalDate) {
        val result = _state.value.endDate
            .withYear(endDate.year)
            .withMonth(endDate.monthValue)
            .withDayOfMonth(endDate.dayOfMonth)

        _state.update {
            it.copy(
                endDate = result,
            )
        }
    }

    private fun updateEndTime(endTime: LocalTime) {
        val result = _state.value.endDate
            .withHour(endTime.hour)
            .withMinute(endTime.minute)

        _state.update {
            it.copy(
                endDate = result,
            )
        }
    }

    private fun popBackStack() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.PopBackStack)
        }
    }

    private fun loadDetail() {
        savedStateHandle.get<String>(EVENT_ID)?.let { taskId ->
            viewModelScope.launch {
                // TODO: complete
            }
        }
    }

    private fun editTitleOrDescription(text: String, editType: EditType) {
        viewModelScope.launch {
            sendUiEvent(UiEvent.Navigate(EditScreenDestination(text, editType).route))
        }
    }

    private fun getSyncType(agendaItem: AgendaItem.Reminder): SyncType {
        return savedStateHandle.get<String>(EVENT_ID)?.let {
            if (agendaItem.syncState == SyncType.SYNCED) SyncType.UPDATE else agendaItem.syncState
        } ?: SyncType.CREATE
    }

    private fun getDialogData(ex: ValidationException): TaskyDialogType {
        return TaskyDialogType.Error(
            title = UiText.StringResource(R.string.attendee),
            description = ex.toUiText(),
            dialogOptions = TaskyDialogOptions.SingleOption(
                confirmationText = UiText.StringResource(CoreR.string.cancel),
            ),
        )
    }
}
