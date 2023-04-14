package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
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
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.composables.VisitorOptionType
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentDescription
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentHeader
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentNotification
import com.artemissoftware.tasky.agenda.composables.assignment.VisitorItem
import com.artemissoftware.tasky.agenda.composables.assignment.VisitorsHeader
import com.artemissoftware.tasky.agenda.presentation.dashboard.composables.PhotoGallery
import com.artemissoftware.tasky.agenda.presentation.detail.DetailEvents
import com.artemissoftware.tasky.agenda.presentation.detail.composables.DetailDivider
import com.artemissoftware.tasky.agenda.presentation.detail.composables.TimeInterval
import com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog.AttendeeDialog
import com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models.Visitor
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditRecipient
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.destinations.EditScreenDestination
import com.artemissoftware.tasky.util.DateTimePicker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.getOr

@Destination
@Composable
fun EventDetailScreen(
    navigator: DestinationsNavigator,
    eventId: String? = null,
    userId: String,
    resultRecipient: ResultRecipient<EditScreenDestination, EditRecipient>,
/* TODO: add viewmodel here*/
) {
    resultRecipient.onNavResult { result ->
        result.getOr { null }?.let { editResult -> // TODO If by any chance it returns null, you could pop the backstack, so you get back to the agenda screen

            when (editResult.editType) {
                EditType.Description -> {
                    // TODO: add when viewmodel is ready
                    // viewModel.onTriggerEvent(DetailEvents.UpdateDescription(editResult.text))
                }
                EditType.Title -> {
                    // TODO: add when viewmodel is ready
                    // viewModel.onTriggerEvent(DetailEvents.UpdateTitle(editResult.text))
                }
            }
        }
    }

    // TODO: call EventDetailScreenContent when viewmodel is ready
}

@Composable
private fun EventDetailScreenContent(
    state: EventDetailState,
    events: (DetailEvents) -> Unit,
) {
    val context = LocalContext.current

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
                title = String.format(
                    stringResource(id = R.string.edit_title_with_argument),
                    stringResource(id = R.string.event),
                ),
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
                                events(DetailEvents.ToggleEdition)
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
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            item {
                                AssignmentHeader(
                                    agendaItemType = AgendaItemType.Task(),
                                    title = state.title,
                                    modifier = Modifier.fillMaxWidth(),
                                    isEditing = state.isEditing,
                                    onEditClick = {
                                        events(DetailEvents.EditTitle(it))
                                    },
                                    onIsDoneClick = {
                                        events(DetailEvents.ToggleIsDone)
                                    },
                                )

                                DetailDivider(top = 20.dp, bottom = 20.dp, modifier = Modifier.fillMaxWidth())
                            }
                            item {
                                AssignmentDescription(
                                    isEditing = state.isEditing,
                                    description = state.description,
                                    modifier = Modifier.fillMaxWidth(),
                                    onEditClick = {
                                        events(DetailEvents.EditDescription(it))
                                    },
                                )
                            }
                            item {
                                PhotoGallery(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(112.dp),
                                    isEditing = state.isEditing,
                                    onAddPhotoClick = {
                                        // TODO: add event here
                                    },
                                    photos = state.photos,
                                )

                                DetailDivider(top = 20.dp, bottom = 28.dp, modifier = Modifier.fillMaxWidth())
                            }
                            item {
                                TimeInterval(
                                    modifier = Modifier.fillMaxWidth(),
                                    isEditing = state.isEditing,
                                    startDate = state.startDate,
                                    onStartDateClick = {
                                        DateTimePicker.datePickerDialog(
                                            context = context,
                                            date = state.startDate.toLocalDate(),
                                            onDateSelected = {
                                                events(DetailEvents.UpdateStartDate(it))
                                            },
                                        ).show()
                                    },
                                    onStartTimeClick = {
                                        DateTimePicker.timePickerDialog(
                                            context = context,
                                            time = state.startDate.toLocalTime(),
                                            onTimeSelected = {
                                                events(DetailEvents.UpdateStartTime(it))
                                            },
                                        ).show()
                                    },
                                    endDate = state.endDate,
                                    onEndDateClick = {
                                        DateTimePicker.datePickerDialog(
                                            context = context,
                                            date = state.endDate.toLocalDate(),
                                            onDateSelected = {
                                                events(DetailEvents.UpdateEndDate(it))
                                            },
                                        ).show()
                                    },
                                    onEndTimeClick = {
                                        DateTimePicker.timePickerDialog(
                                            context = context,
                                            time = state.endDate.toLocalTime(),
                                            onTimeSelected = {
                                                events(DetailEvents.UpdateEndTime(it))
                                            },
                                        ).show()
                                    },
                                )

                                DetailDivider(top = 28.dp, bottom = 20.dp, modifier = Modifier.fillMaxWidth())
                            }
                            item {
                                AssignmentNotification(
                                    isEditing = state.isEditing,
                                    modifier = Modifier.fillMaxWidth(),
                                    onNotificationSelected = {
                                        events(DetailEvents.UpdateNotification(it))
                                    },
                                    selectedNotification = state.notification,
                                )

                                DetailDivider(top = 20.dp, bottom = 30.dp, modifier = Modifier.fillMaxWidth())
                            }
                            item {
                                VisitorsHeader(
                                    visitorOptionType = state.visitorOption,
                                    isEditing = state.isEditing,
                                    modifier = Modifier.fillMaxWidth(),
                                    onViewVisitorsClick = { events(DetailEvents.ViewVisitors(visitorOptionType = it)) },
                                )
                            }
                            visitors(
                                type = VisitorOptionType.GOING,
                                selectedOption = state.visitorOption,
                                visitors = state.getGoingVisitors(),
                                onDeleteVisitor = { attendeeId ->
                                    events(DetailEvents.DeleteVisitor(attendeeId = attendeeId))
                                },
                            )
                            visitors(
                                type = VisitorOptionType.NOT_GOING,
                                selectedOption = state.visitorOption,
                                visitors = state.getNotGoingVisitors(),
                                onDeleteVisitor = { attendeeId ->
                                    events(DetailEvents.DeleteVisitor(attendeeId = attendeeId))
                                },
                            )
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    TaskyTextButton(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = String.format(
                                            stringResource(id = R.string.delete_title_with_argument),
                                            stringResource(id = R.string.event),
                                        ),
                                        onClick = {
                                            events(DetailEvents.Save)
                                        },
                                    )
                                }
                            }
                        }

                        AttendeeDialog(
                            modifier = Modifier
                                .fillMaxWidth(),
                            email = state.attendeeDialogState.email,
                            showDialog = state.attendeeDialogState.showDialog,
                            errorText = state.attendeeDialogState.errorMessage,
                            onEmailChange = {
                                events(DetailEvents.UpdateAttendeeEmail(email = it))
                            },
                            onCloseClick = {
                                events(DetailEvents.CloseAttendeeDialog)
                            },
                            onAddClick = {
                                events(DetailEvents.AddAttendee)
                            },
                        )
                    }
                },
            )
        },
    )
}

private fun LazyListScope.visitors(
    type: VisitorOptionType,
    selectedOption: VisitorOptionType,
    visitors: List<Visitor>,
    onDeleteVisitor: (String) -> Unit,
) {
    if ((selectedOption == VisitorOptionType.ALL || type == selectedOption) && visitors.isNotEmpty()) {
        item {
            TaskyText(text = stringResource(id = type.textId))
        }
        items(
            items = visitors,
            key = {
                it.attendee.id
            },
            itemContent = { visitor ->
                VisitorItem(
                    visitor = visitor,
                    onDeleteVisitor = { attendeeId ->
                        onDeleteVisitor(attendeeId)
                    },
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailScreenContentContentPreview() {
    EventDetailScreenContent(
        state = EventDetailState(
            // agendaItem = AgendaItem.mockTask,
        ),
        events = {},
    )
}

@Preview(showBackground = true)
@Composable
fun EventDetailScreenContentEditingPreview() {
    EventDetailScreenContent(
        state = EventDetailState(
            isEditing = true,
            // agendaItem = AgendaItem.mockTask,
        ),
        events = {},
    )
}
