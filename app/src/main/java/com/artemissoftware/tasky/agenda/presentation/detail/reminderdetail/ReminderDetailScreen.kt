package com.artemissoftware.tasky.agenda.presentation.detail.reminderdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.util.DateTimePatternsConstants
import com.artemissoftware.core.util.extensions.format
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentDescription
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentHeader
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentNotification
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Notification
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.detail.DetailState
import com.artemissoftware.tasky.agenda.presentation.detail.composables.DetailDivider
import com.artemissoftware.tasky.agenda.presentation.detail.composables.TimeInterval
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.authentication.presentation.login.ManageUIEvents
import com.artemissoftware.tasky.destinations.EditScreenDestination
import com.artemissoftware.tasky.util.DateTimePicker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.getOr

@Destination
@Composable
fun ReminderDetailScreen(
    navigator: DestinationsNavigator,
    viewModel: ReminderDetailViewModel = hiltViewModel(),
    reminderId: String? = null,
    resultRecipient: ResultRecipient<EditScreenDestination, ReminderRecipient>
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val notifications = viewModel.notifications.value

    LaunchedEffect(key1 = Unit) {
        // TODO: check if with hilt navigation I can get this object directly on the viewmodel and make this call on init
        viewModel.onTriggerEvent(DetailEvents.LoadDetail(id = reminderId))
    }

    resultRecipient.onNavResult { result ->
        result.getOr { null }?.let { editResult ->

            when(editResult.editType){
                EditType.Description -> {
                    viewModel.onTriggerEvent(DetailEvents.UpdateDescription(editResult.text))
                }
                EditType.Title -> {
                    viewModel.onTriggerEvent(DetailEvents.UpdateTitle(editResult.text))
                }
            }
        }
    }

    ReminderDetailScreenContent(
        state = state.value,
        events = viewModel::onTriggerEvent,
        notifications = notifications,
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigate = {
            navigator.navigate(it.route)
        },
        onPopBackStack = {
            navigator.popBackStack()
        },
    )
}

@Composable
fun ReminderDetailScreenContent(
    state: DetailState,
    events: (DetailEvents) -> Unit,
    notifications: List<Notification>,
) {
    val context = LocalContext.current
    val resources = context.resources

    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
        topBar = {
            TaskyTopBar(
                onBackClicked = {
                    events(DetailEvents.PopBackStack)
                },
                backGroundColor = Black,
                allCaps = true,
                title = String.format(resources.getString(R.string.edit_title_with_argument), resources.getString(R.string.reminder)),
                toolbarActions = { color ->

                    if (state.isEditing) {
                        TaskyToolBarAction(
                            text = stringResource(id = R.string.save),
                            tint = color,
                            onClicked = {
                                events(DetailEvents.Save)
                            },
                        )
                    } else {
                        TaskyToolBarAction(
                            iconId = R.drawable.ic_edit,
                            tint = color,
                            onClicked = {
                                events(DetailEvents.Edit)
                            },
                        )
                    }
                },
            )
        },
        content = {
            TaskyContentSurface(

                content = {
                    Box(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .padding(bottom = 68.dp)
                            .padding(horizontal = 16.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopCenter),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            AssignmentHeader(
                                agendaItemType = AgendaItemType.Reminder(),
                                title = state.agendaItem?.itemTitle ?: "",
                                modifier = Modifier.fillMaxWidth(),
                                isEditing = state.isEditing,
                                onEditClick = {
                                    events(DetailEvents.EditTitle(it))
                                },
                            )

                            DetailDivider(top = 20.dp, bottom = 20.dp)

                            AssignmentDescription(
                                isEditing = state.isEditing,
                                description = state.agendaItem?.itemDescription ?: "",
                                modifier = Modifier.fillMaxWidth(),
                                onEditClick = {
                                    events(DetailEvents.EditDescription(it))
                                },
                            )

                            DetailDivider(top = 20.dp, bottom = 28.dp)

                            TimeInterval(
                                isEditing = state.isEditing,
                                startTime = state.startDate.toLocalTime().format(pattern = DateTimePatternsConstants.TIME_PATTERN_HH_mm),
                                onStartTimeClick = {
                                    DateTimePicker.timePickerDialog(
                                        context = context,
                                        time = state.startDate.toLocalTime(),
                                        onTimeSelected = {
                                            events(DetailEvents.UpdateStartTime(it))
                                        },
                                    ).show()
                                },
                                startDate = state.startDate.format(pattern = DateTimePatternsConstants.DATE_PATTERN_MMM_dd_yyyy),
                                onStartDateTimeClick = {
                                    DateTimePicker.datePickerDialog(
                                        context = context,
                                        date = state.startDate.toLocalDate(),
                                        onDateSelected = {
                                            events(DetailEvents.UpdateStartDate(it))
                                        },
                                    ).show()
                                },
                            )

                            DetailDivider(top = 28.dp, bottom = 20.dp)

                            notifications.find { it.isDefault }?.let { defaultNotification ->
                                AssignmentNotification(
                                    isEditing = state.isEditing,
                                    modifier = Modifier.fillMaxWidth(),
                                    notificationOptions = notifications,
                                    onNotificationSelected = {
                                        events(DetailEvents.UpdateNotification(it))
                                    },
                                    selectedNotification = state.agendaItem?.itemNotification ?: defaultNotification,
                                )
                            }

                            DetailDivider(top = 20.dp, bottom = 30.dp)
                        }
                        Column(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            DetailDivider(top = 20.dp, bottom = 20.dp)
                            TaskyTextButton(
                                text = String.format(resources.getString(R.string.delete_title_with_argument), resources.getString(R.string.reminder)),
                                onClick = {
                                    events(DetailEvents.Save)
                                },
                            )
                        }
                    }
                },
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ReminderDetailScreenContentPreview() {
    ReminderDetailScreenContent(
        state = DetailState(
            agendaItemType = AgendaItemType.Reminder(),
            agendaItem = AgendaItem.mockReminder,
        ),
        events = {},
        notifications = listOf(Notification(1, 30, "30 minutes before", true)),
    )
}

@Preview(showBackground = true)
@Composable
fun ReminderDetailScreenContentEditingPreview() {
    ReminderDetailScreenContent(
        state = DetailState(
            isEditing = true,
            agendaItemType = AgendaItemType.Reminder(),
            agendaItem = AgendaItem.mockReminder,
        ),
        events = {},
        notifications = listOf(Notification(1, 30, "30 minutes before", true)),
    )
}