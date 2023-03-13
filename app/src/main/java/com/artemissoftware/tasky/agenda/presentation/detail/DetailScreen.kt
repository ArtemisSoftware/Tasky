package com.artemissoftware.tasky.agenda.presentation.detail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
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
import com.artemissoftware.core.util.safeLet
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.composables.assignment.*
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Photo
import com.artemissoftware.tasky.agenda.presentation.dashboard.composables.PhotoGallery
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun DetailScreen(
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

    val startDate = remember { mutableStateOf(LocalDate.now()) }
    val formattedStartDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(startDate.value)
        }
    }

    val endDate = remember { mutableStateOf(LocalDate.now()) }
    val formattedEndDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(endDate.value)
        }
    }




    val startTime = remember { mutableStateOf(LocalTime.now()) }
    val formattedStartTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("HH:mm").format(startTime.value)
        }
    }

    val endTime = remember { mutableStateOf(LocalTime.now()) }
    val formattedEndTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("HH:mm").format(endTime.value)
        }
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
                                )

                                TaskyDivider(top = 20.dp, bottom = 20.dp)

                                AssignmentDescription(
                                    isEditing = state.isEditing,
                                    description = item.itemDescription,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                if(type is AgendaItemType.Event) {
                                    PhotoGallery(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(112.dp),
                                        isEditing = state.isEditing,
                                        onAddPhotoClick = {},
                                        photos = Photo.mockPhotos
                                    )
                                }

                                TaskyDivider(top = 20.dp, bottom = 28.dp)

                                TimeInterval(
                                    type = type,
                                    isEditing = state.isEditing,
                                    startTime = formattedStartTime,
                                    onStartTimeClick = {
                                        getTimePickerDialog(context = context, time = startTime).show()
                                    },
                                    startDate = formattedStartDate,
                                    onStartDateTimeClick = {
                                        getDatePickerDialog(context = context, date = startDate).show()
                                    },
                                    endTime = formattedEndTime,
                                    onEndTimeClick = {
                                        getTimePickerDialog(context = context, time = endTime).show()
                                    },
                                    endDate = formattedEndDate,
                                    onEndDateTimeClick = {
                                        getDatePickerDialog(context = context, date = endDate).show()
                                    }
                                )

                                TaskyDivider(top = 28.dp, bottom = 20.dp)

                                AssignmentNotification(
                                    isEditing = state.isEditing,
                                    description = "First description",
                                    modifier = Modifier.fillMaxWidth()
                                )

                                TaskyDivider(top = 20.dp, bottom = 30.dp)

                                if(type is AgendaItemType.Event) {
                                    VisitorsHeader(
                                        isEditing = state.isEditing,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    VisitorList(
                                        modifier = Modifier.padding(top = 20.dp),
                                        isEditing = state.isEditing
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                TaskyDivider(top = 20.dp, bottom = 20.dp)
                                TaskyTextButton(
                                    text = buttonTitle.value,
                                    onClick = {}
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


private fun getDatePickerDialog(context: Context, date: MutableState<LocalDate>): DatePickerDialog{

    return DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->

            date.value = LocalDate.of(mYear, mMonth + 1, mDayOfMonth)
        },
        date.value.year,
        date.value.monthValue - 1,
        date.value.dayOfMonth
    )

}

private fun getTimePickerDialog(context: Context, time: MutableState<LocalTime>): TimePickerDialog{

    return TimePickerDialog(
        context,
        {_, mHour : Int, mMinute: Int ->
            time.value = LocalTime.of(mHour, mMinute)
        },
        time.value.hour,
        time.value.minute,
        true
    )

}


@Preview(showBackground = true)
@Composable
fun DetailScreenReminderPreview() {
    DetailScreen(
        state = DetailState(
            agendaItemType = AgendaItemType.Reminder(),
            agendaItem = AgendaItem.mockReminder
        ),
        events = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DetailScreenReminderEditingPreview() {
    DetailScreen(
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