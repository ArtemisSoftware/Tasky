package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail

import android.net.Uri
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
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.artemissoftware.tasky.agenda.domain.usecase.attendee.GetAttendeeUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.event.DeleteEventUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.event.GetEventUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.event.SaveEventUseCase
import com.artemissoftware.tasky.agenda.domain.usecase.event.ValidatePicturesUseCase
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog.AttendeeDialogState
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.agenda.util.NavigationConstants.EVENT_ID
import com.artemissoftware.tasky.agenda.util.NavigationConstants.USER_ID
import com.artemissoftware.tasky.destinations.EditScreenDestination
import com.artemissoftware.tasky.destinations.PhotoScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject
import com.artemissoftware.core.R as CoreR

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val validatePicturesUseCase: ValidatePicturesUseCase,
    private val getAttendeeUseCase: GetAttendeeUseCase,
    private val getEventUseCase: GetEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val saveEventUseCase: SaveEventUseCase,
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
            DetailEvents.Save -> { validatePictures() }
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
            is DetailEvents.AddPicture -> {
                addPicture(uri = event.uri)
            }

            is DetailEvents.DeleteVisitor -> {
                deleteVisitor(event.attendeeId)
            }
            is DetailEvents.GoToPicture -> {
                goToPicture(event.picture)
            }
            is DetailEvents.RemovePicture -> { removePicture(event.pictureId) }
            DetailEvents.Delete -> {
                deleteEventWarning()
            }
            DetailEvents.JoinEvent -> TODO()
            DetailEvents.LeaveEvent -> TODO()
            else -> Unit
        }
    }

    private fun removePicture(pictureId: String) = with(_state) {
        update {
            val listPictures = it.pictures.toMutableList()
            listPictures.removeIf { it.id == pictureId }

            it.copy(
                pictures = listPictures,
                deletedPictures = it.deletedPictures + pictureId,
            )
        }
    }

    private fun goToPicture(picture: Picture) {
        viewModelScope.launch {
            sendUiEvent(UiEvent.Navigate(PhotoScreenDestination(picture = picture).route))
        }
    }

    private fun addPicture(uri: Uri) = with(_state) {
        update {
            it.copy(
                pictures = it.pictures + Picture.Local(uri = uri.toString(), picId = UUID.randomUUID().toString()),
            )
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
                is Resource.Success -> {
                    result.data?.let { attendee ->
                        addAttendee(attendee = attendee)
                    }
                }
                is Resource.Loading -> Unit
            }
        }
    }

    private fun addAttendee(attendee: Attendee) = with(_state) {
        update {
            it.copy(
                attendees = it.attendees + attendee,
            )
        }
    }

    private fun deleteVisitor(attendeeId: String) = with(_state) {
        update {
            val list = it.attendees.toMutableList()
            list.removeIf { it.id == attendeeId }
            it.copy(
                attendees = list.toList(),
            )
        }
    }

    private fun updateVisitorsSelection(visitorOptionType: VisitorOptionType) = with(_state) {
        update {
            it.copy(
                visitorOption = visitorOptionType,
            )
        }
    }

    private fun openAttendeeDialog() = with(_state) {
        update {
            it.copy(
                attendeeDialogState = AttendeeDialogState(showDialog = true),
            )
        }
    }

    private fun closeAttendeeDialog() = with(_state) {
        update {
            it.copy(
                attendeeDialogState = AttendeeDialogState(),
            )
        }
    }

    private fun updateAttendeeEmail(email: String) = with(_state) {
        update {
            it.copy(
                attendeeDialogState = it.attendeeDialogState.copy(email = email),
            )
        }
    }

    private fun toggleEdition() = with(_state) {
        update {
            it.copy(
                isEditing = !it.isEditing,
            )
        }
    }

    private fun updateDescription(text: String) = with(_state) {
        update {
            it.copy(
                description = text,
            )
        }
    }

    private fun updateTitle(text: String) = with(_state) {
        update {
            it.copy(
                title = text,
            )
        }
    }

    private fun updateNotification(notification: NotificationType) = with(_state) {
        update {
            it.copy(
                notification = notification,
            )
        }
    }

    private fun updateStartDate(startDate: LocalDate) = with(_state) {
        update {
            it.copy(
                startDate = it.startDate.with(startDate),
            )
        }
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
    }

    private fun updateEndDate(endDate: LocalDate) = with(_state) {
        update {
            it.copy(
                endDate = it.endDate.with(endDate),
            )
        }
    }

    private fun updateEndTime(endTime: LocalTime) = with(_state) {
        val result = value.endDate
            .withHour(endTime.hour)
            .withMinute(endTime.minute)

        update {
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

    private fun loadDetail() = with(_state) {
        savedStateHandle.get<String>(EVENT_ID)?.let { eventId ->
            viewModelScope.launch {
                val result = getEventUseCase(eventId)
                result?.let { item ->
                    update {
                        it.copy(
                            agendaItem = item,
                            notification = NotificationType.getNotification(
                                remindAt = item.remindAt,
                                startDate = item.from
                            ),
                            startDate = item.from,
                            title = item.title,
                            description = item.description ?: "",
                            endDate = item.to,
                            pictures = item.pictures,
                            attendees = item.attendees,
                            hostId = item.hostId,
                        )
                    }
                }
            }
        } ?: run {
            savedStateHandle.get<String>(USER_ID)?.let { userId ->
                update {
                        it.copy(
                            hostId = userId,
                        )
                }
            }
        }
    }

    private fun editTitleOrDescription(text: String, editType: EditType) {
        viewModelScope.launch {
            sendUiEvent(UiEvent.Navigate(EditScreenDestination(text, editType).route))
        }
    }

    private fun validatePictures() = with(_state.value) {
        viewModelScope.launch {
            val result = validatePicturesUseCase.invoke(pictures = pictures)

            if (result.numberOfRejectedPictures != 0) {
                sendUiEvent(UiEvent.ShowSnackBar(UiText.DynamicString("${result.numberOfRejectedPictures} photos were skipped because they were too large")))
            }

            saveEvent(validatedPictures = result.validPictures)
        }
    }

    private fun saveEvent(validatedPictures: List<Picture>) = with(_state.value) {
        val item = AgendaItem.Event(
            id = agendaItem.id,
            title = title,
            description = description,
            remindAt = NotificationType.remindAt(time = startDate, notificationType = notification),
            from = startDate,
            to = endDate,
            syncState = getSyncType(agendaItem),
            hostId = hostId,
            attendees = attendees,
            pictures = validatedPictures,
            deletedPictures = deletedPictures,
        )

        viewModelScope.launch {
            saveEventUseCase(item)
            popBackStack()
        }
    }

    private fun deleteEventWarning() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.ShowDialog(getDeleteWarningDialogData()))
        }
    }

    private fun deleteEvent() {
        savedStateHandle.get<String>(EVENT_ID)?.let { eventId ->
            viewModelScope.launch {
                deleteEventUseCase(id = eventId)
                popBackStack()
            }
        }
    }

    private fun getSyncType(agendaItem: AgendaItem.Event): SyncType {
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

    private fun getDeleteWarningDialogData(): TaskyDialogType {
        return TaskyDialogType.Error(
            title = UiText.StringResource(R.string.event),
            description = UiText.StringResource(R.string.are_you_sure_delete_event),
            dialogOptions = TaskyDialogOptions.DoubleOption(
                confirmationText = UiText.StringResource(CoreR.string.ok),
                confirmation = {
                    deleteEvent()
                },
                cancelText = UiText.StringResource(CoreR.string.cancel),
            ),
        )
    }
}
