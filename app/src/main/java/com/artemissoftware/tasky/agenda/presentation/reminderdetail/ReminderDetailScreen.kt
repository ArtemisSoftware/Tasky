package com.artemissoftware.tasky.agenda.presentation.reminderdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentDescription
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentHeader
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentNotification
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.detail.DetailState
import com.artemissoftware.tasky.agenda.presentation.detail.composables.DetailDivider
import com.artemissoftware.tasky.agenda.presentation.detail.composables.TimeInterval
import com.artemissoftware.tasky.util.DateTimePicker

@Composable
fun ReminderDetailScreen(/*Add view model when ready*/) {
    // TODO extract data like on other screens to call ReminderDetailScreenContent
}

@Composable
fun ReminderDetailScreenContent(
    state: DetailState,
    events: (DetailEvents) -> Unit,
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

                            DetailDivider(top = 20.dp, bottom = 20.dp, modifier = Modifier.fillMaxWidth())

                            AssignmentDescription(
                                isEditing = state.isEditing,
                                description = state.agendaItem?.itemDescription ?: "",
                                modifier = Modifier.fillMaxWidth(),
                                onEditClick = {
                                    events(DetailEvents.EditDescription(it))
                                },
                            )

                            DetailDivider(top = 20.dp, bottom = 28.dp, modifier = Modifier.fillMaxWidth())

                            TimeInterval(
                                modifier = Modifier.fillMaxWidth(),
                                isEditing = state.isEditing,
                                startDate = state.startDate,
                                onStartTimeClick = {
                                    DateTimePicker.timePickerDialog(
                                        context = context,
                                        time = state.startDate.toLocalTime(),
                                        onTimeSelected = {
                                            events(DetailEvents.UpdateStartTime(it))
                                        },
                                    ).show()
                                },
                                onStartDateClick = {
                                    DateTimePicker.datePickerDialog(
                                        context = context,
                                        date = state.startDate.toLocalDate(),
                                        onDateSelected = {
                                            events(DetailEvents.UpdateStartDate(it))
                                        },
                                    ).show()
                                },
                            )

                            DetailDivider(top = 28.dp, bottom = 20.dp, modifier = Modifier.fillMaxWidth())

                            AssignmentNotification( // TODO: add a context menu here
                                isEditing = state.isEditing,
                                description = "First description", // TODO: replace with default option. Do this on next PR
                                modifier = Modifier.fillMaxWidth(),
                                notificationOptions = emptyList(), // TODO: replace with data form the database when viewmodel is ready
                                onNotificationSelected = {
                                    events(DetailEvents.UpdateNotification(it))
                                },
                            )

                            DetailDivider(top = 20.dp, bottom = 30.dp, modifier = Modifier.fillMaxWidth())
                        }
                        Column(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            DetailDivider(top = 20.dp, bottom = 20.dp, modifier = Modifier.fillMaxWidth())
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
    )
}
