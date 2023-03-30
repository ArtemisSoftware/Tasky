package com.artemissoftware.tasky.agenda.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.util.DateTimePatternsConstants
import com.artemissoftware.core.util.extensions.format
import com.artemissoftware.core.util.safeLet
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.composables.assignment.*
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Photo
import com.artemissoftware.tasky.agenda.presentation.dashboard.composables.PhotoGallery
import com.artemissoftware.tasky.util.DateTimePicker
import com.ramcosta.composedestinations.annotation.Destination
import java.util.*


@Destination
@Composable
fun DetailScreen(/* TODO :Add view model here*/) {

    /* TODO :Add content here*/
}

@Composable
private fun DetailScreenContent(
    state: DetailState,
    events: (DetailEvents) -> Unit
) {

    val context = LocalContext.current
    val resources = context.resources


    val title = remember(state.isEditing) {

        val screenTitle = state.agendaItemType?.let{
            String.format(resources.getString(R.string.edit_title_with_argument), resources.getString(it.title))
        } ?: run {
            "Date"
        }
        mutableStateOf(screenTitle)
    }

    val buttonTitle = remember {

        val title = state.agendaItemType?.let{
            String.format(resources.getString(R.string.delete_title_with_argument), resources.getString(it.title))
        } ?: run {
            ""
        }
        mutableStateOf(title)
    }

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
                title = title.value,
                toolbarActions = { color->

                    if(state.isEditing){
                        TaskyToolBarAction(
                            text = stringResource(id = R.string.save),
                            tint = color,
                            onClicked = {
                                events(DetailEvents.Save)
                            }
                        )
                    }
                    else {
                        TaskyToolBarAction(
                            iconId = R.drawable.ic_edit,
                            tint = color,
                            onClicked = {
                                events(DetailEvents.Edit)
                            }
                        )
                    }
                }
            )
        },
        content = {

            TaskyContentSurface(

                content = {

                    safeLet(state.agendaItemType, state.agendaItem) { type, item ->

                        Box(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .padding(bottom = 68.dp)
                                .padding(horizontal = 16.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                AssignmentHeader(
                                    agendaItemType = type,
                                    title = item.itemTitle,
                                    modifier = Modifier.fillMaxWidth(),
                                    isEditing = state.isEditing,
                                    onEditClick = {
                                        events(DetailEvents.EditTitle(it))
                                    }
                                )

                                TaskyDivider(top = 20.dp, bottom = 20.dp)

                                AssignmentDescription(
                                    isEditing = state.isEditing,
                                    description = item.itemDescription ?: "",
                                    modifier = Modifier.fillMaxWidth(),
                                    onEditClick = {
                                        events(DetailEvents.EditDescription(it))
                                    }
                                )

                                if(type is AgendaItemType.Event) {
                                    PhotoGallery(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(112.dp),
                                        isEditing = state.isEditing,
                                        onAddPhotoClick = {
                                                          // TODO: add event here
                                        },
                                        photos = Photo.mockPhotos // TODO: change this in the future when events are ready
                                    )
                                }

                                TaskyDivider(top = 20.dp, bottom = 28.dp)

                                TimeInterval(
                                    type = type,
                                    isEditing = state.isEditing,
                                    startTime = state.startDate.toLocalTime().format(pattern = DateTimePatternsConstants.TIME_PATTERN_HH_mm),
                                    onStartTimeClick = {

                                        DateTimePicker.timePickerDialog(
                                            context = context,
                                            time = state.startDate.toLocalTime(),
                                            onTimeSelected = {
                                                events(DetailEvents.UpdateStartTime(it))
                                            }
                                        ).show()
                                    },
                                    startDate = state.startDate.format(pattern = DateTimePatternsConstants.DATE_PATTERN_MMM_dd_yyyy),
                                    onStartDateTimeClick = {

                                        DateTimePicker.datePickerDialog(
                                            context = context,
                                            date = state.startDate.toLocalDate(),
                                            onDateSelected = {
                                                events(DetailEvents.UpdateStartDate(it))
                                            }
                                        ).show()
                                    },
                                    endTime = state.endDate.toLocalTime().format(pattern = DateTimePatternsConstants.TIME_PATTERN_HH_mm),
                                    onEndTimeClick = {

                                        DateTimePicker.timePickerDialog(
                                            context = context,
                                            time = state.endDate.toLocalTime(),
                                            onTimeSelected = {
                                                events(DetailEvents.UpdateEndTime(it))
                                            }
                                        ).show()
                                    },
                                    endDate = state.endDate.format(pattern = DateTimePatternsConstants.DATE_PATTERN_MMM_dd_yyyy),
                                    onEndDateTimeClick = {
                                        DateTimePicker.datePickerDialog(
                                            context = context,
                                            date = state.endDate.toLocalDate(),
                                            onDateSelected = {
                                                events(DetailEvents.UpdateEndDate(it))
                                            }
                                        ).show()
                                    }
                                )

                                TaskyDivider(top = 28.dp, bottom = 20.dp)

                                AssignmentNotification( // TODO: add a context menu here
                                    isEditing = state.isEditing,
                                    description = "First description", //TODO: replace with default option. Do this on next PR
                                    modifier = Modifier.fillMaxWidth(),
                                    notificationOptions = emptyList(), //TODO: replace with data form the database when viewmodel is ready
                                    onNotificationSelected = {
                                        events(DetailEvents.UpdateNotification(it))
                                    }
                                )

                                TaskyDivider(top = 20.dp, bottom = 30.dp)

                                if(type is AgendaItemType.Event) {
                                    VisitorsHeader(
                                        isEditing = state.isEditing,
                                        modifier = Modifier.fillMaxWidth()
                                    )


                                    // TODO: add visitor list here
                                }
                            }
                            Column(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                TaskyDivider(top = 20.dp, bottom = 20.dp)
                                TaskyTextButton(
                                    text = buttonTitle.value,
                                    onClick = {
                                        events(DetailEvents.Save)
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
private fun TaskyDivider (top: Dp, bottom: Dp = 0.dp) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = top, bottom = bottom),
        color = Light
    )
}

@Composable
private fun TimeInterval(
    type: AgendaItemType,
    isEditing: Boolean,
    startTime: String,
    onStartTimeClick: () -> Unit,
    startDate: String,
    onStartDateTimeClick: () -> Unit,
    endTime: String,
    onEndTimeClick: () -> Unit,
    endDate: String,
    onEndDateTimeClick: () -> Unit,
) {

    when(type){
        is AgendaItemType.Event -> {
            AssignmentTime(
                isEditing = isEditing,
                title = R.string.from,
                day = startDate,
                hour = startTime,
                modifier = Modifier.fillMaxWidth(),
                onTimeClick = {
                    onStartTimeClick()
                },
                onDateClick = {
                    onStartDateTimeClick()
                }
            )

            TaskyDivider(top = 28.dp, bottom = 28.dp)

            AssignmentTime(
                isEditing = isEditing,
                title = R.string.to,
                day = endDate,
                hour = endTime,
                modifier = Modifier.fillMaxWidth(),
                onTimeClick = {
                    onEndTimeClick()
                },
                onDateClick = {
                    onEndDateTimeClick()
                }
            )
        }
        is AgendaItemType.Reminder, is AgendaItemType.Task-> {
            AssignmentTime(
                isEditing = isEditing,
                title = R.string.at,
                day = startDate,
                hour = startTime,
                modifier = Modifier.fillMaxWidth(),
                onTimeClick = {
                    onStartTimeClick()
                },
                onDateClick = {
                    onStartDateTimeClick()
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DetailScreenContentReminderPreview() {
    DetailScreenContent(
        state = DetailState(
            agendaItemType = AgendaItemType.Reminder(),
            agendaItem = AgendaItem.mockReminder
        ),
        events = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DetailScreenContentReminderEditingPreview() {
    DetailScreenContent(
        state = DetailState(
            isEditing = true,
            agendaItemType = AgendaItemType.Reminder(),
            agendaItem = AgendaItem.mockReminder
        ),
        events = {}
    )
}
/*
@Preview(showBackground = true)
@Composable
fun DetailScreenTaskPreview() {
    DetailScreen(
        state = DetailState(
            agendaItemType = AgendaItemType.Task(),
            agendaItem = AgendaItem.mockReminder
        ),
        events = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DetailScreenTaskEditingPreview() {
    DetailScreen(
        state = DetailState(
            isEditing = true,
            agendaItemType = AgendaItemType.Task(),
            agendaItem = AgendaItem.mockReminder
        ),
        events = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DetailScreenEventPreview() {
    DetailScreen(
        state = DetailState(
            agendaItemType = AgendaItemType.Event(),
            agendaItem = AgendaItem.mockReminder
        ),
        events = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DetailScreenEventEditingPreview() {
    DetailScreen(
        state = DetailState(
            isEditing = true,
            agendaItemType = AgendaItemType.Event(),
            agendaItem = AgendaItem.mockReminder
        ),
        events = {}
    )
}
*/