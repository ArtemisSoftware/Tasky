package com.artemissoftware.tasky.agenda.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.artemissoftware.core.presentation.composables.TaskyAvatar
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyExpandableSquareButton
import com.artemissoftware.core.presentation.composables.dropdown.TaskyDropDownItem
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.menu.TaskyPopupMenu
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.LightBlue
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.core.util.DateTimePatternsConstants
import com.artemissoftware.core.util.DateTimePatternsConstants.DATE_PATTERN_MONTH
import com.artemissoftware.core.util.extensions.format
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.composables.WeekDay
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentCard
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.DayOfWeek
import com.artemissoftware.tasky.agenda.presentation.dashboard.composables.AgendaTopBar
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItemOption
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItems
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaUserOption
import com.artemissoftware.tasky.authentication.presentation.login.ManageUIEvents
import com.artemissoftware.tasky.destinations.AgendaScreenDestination
import com.artemissoftware.tasky.util.DateTimePicker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDate
import java.time.LocalDateTime

@Destination(start = true)
@Composable
fun AgendaScreen(
    navigator: DestinationsNavigator,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    AgendaScreenContent(
        state = state,
        events = viewModel::onTriggerEvent,
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigate = {
            navigator.navigate(it.route)
        },
        onNavigateAndPopCurrent = {
            navigator.navigate(
                route = it.route,
                navOptions = NavOptions.Builder().setPopUpTo(AgendaScreenDestination.route, inclusive = true).build(),
            )
        },
    )
}

@Composable
private fun AgendaScreenContent(
    state: AgendaState,
    events: (AgendaEvents) -> Unit,
) {
    val context = LocalContext.current
    val listWeekDaysState = rememberLazyListState()

    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
        topBar = {
            AgendaTopBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                backGroundColor = Black,
                toolbarActionsLeft = {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {
                                DateTimePicker
                                    .datePickerDialog(
                                        context = context,
                                        date = state.selectedDayOfTheWeek,
                                        onDateSelected = {
                                            events(AgendaEvents.ChangeDate(it))
                                        },
                                    )
                                    .show()
                            },
                    ) {
                        TaskyText(
                            modifier = Modifier,
                            text = state.selectedDayOfTheWeek.format(pattern = DATE_PATTERN_MONTH).uppercase(),
                            color = White,
                        )

                        TaskyIcon(
                            size = 24.dp,
                            modifier = Modifier
                                .alpha(ContentAlpha.medium),
                            icon = Icons.Filled.ArrowDropDown,
                            color = White,
                        )
                    }
                },
                toolbarActions = {
                    TaskyPopupMenu(
                        options = AgendaUserOption.values().toList(),
                        onOptionSelected = {
                            events(AgendaEvents.LogOut)
                        },
                        menuOption = {
                            TaskyDropDownItem(text = stringResource(id = it.descriptionId))
                        },
                        placeHolder = {
                            TaskyAvatar(text = state.userName, circleColor = LightBlue)
                        },
                    )
                },
            )
        },
        floatingActionButton = {
            TaskyExpandableSquareButton(
                icon = R.drawable.ic_add,
                options = AgendaItems.values().toList(),
                onOptionSelected = {
                    events(AgendaEvents.CreateAgendaItem(detailType = it))
                },
                menuOption = {
                    TaskyDropDownItem(text = stringResource(id = it.descriptionId))
                },
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 8.dp),
            ) {
                TaskyContentSurface(
                    content = {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                        ) {
                            LazyRow(
                                state = listWeekDaysState,
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                items(
                                    items = state.daysOfTheWeek,
                                    key = {
                                        it.date
                                    },
                                    itemContent = { item ->

                                        WeekDay(
                                            modifier = Modifier
                                                .size(width = 40.dp, height = 60.dp),
                                            weekDay = item.description,
                                            dayOfTheWeek = item.day,
                                            isSelected = state.selectedDayOfTheWeek == item.date,
                                            onClick = {
                                                events(AgendaEvents.ChangeWeekDay(item.date))
                                            },
                                        )
                                    },
                                )
                            }

                            TaskyText(
                                modifier = Modifier
                                    .padding(vertical = 20.dp),
                                color = Black,
                                style = MaterialTheme.typography.h6,
                                text = if (state.selectedDayOfTheWeek == LocalDate.now()) {
                                    stringResource(id = R.string.today)
                                } else {
                                    state.selectedDayOfTheWeek.format(
                                        DateTimePatternsConstants.DATE_PATTERN_dd_MMM_YYYY,
                                    )
                                },
                            )

                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                items(
                                    items = state.agendaItems,
                                    key = {
                                        it.itemId
                                    },
                                    itemContent = { item ->
                                        AssignmentCard(
                                            agendaItemType = getAgendaItemType(item),
                                            agendaItem = item,
                                            onCheckedChange = {
                                                if (item is AgendaItem.Task) events(AgendaEvents.CompleteAssignment(item.itemId))
                                            },
                                            onOptionClick = {
                                                when (it) {
                                                    AgendaItemOption.OPEN -> {
                                                        events(AgendaEvents.GoToDetail(item = item, isEditing = false))
                                                    }
                                                    AgendaItemOption.EDIT -> {
                                                        events(AgendaEvents.GoToDetail(item = item, isEditing = true))
                                                    }
                                                    AgendaItemOption.DELETE -> {
                                                        events(AgendaEvents.Delete(item = item))
                                                    }
                                                }
                                            },
                                        )
                                    },
                                )
                            }
                        }
                    },
                )
            }
        },
    )
}

private fun getAgendaItemType(item: AgendaItem): AgendaItemType {
    return when (item) {
        is AgendaItem.Reminder -> AgendaItemType.Reminder()
        is AgendaItem.Task -> AgendaItemType.Task()
    }
}

@Preview(showBackground = true)
@Composable
fun AgendaScreenPreview() {
    val localDate = LocalDate.now()
    AgendaScreenContent(
        events = {},
        state = AgendaState(
            userName = "Bruce Wayne",
            daysOfTheWeek = listOf(
                DayOfWeek(date = localDate),
                DayOfWeek(date = localDate.plusDays(1L)),
                DayOfWeek(date = localDate.plusDays(2L)),
                DayOfWeek(date = localDate.plusDays(3L)),
                DayOfWeek(date = localDate.plusDays(4L)),
                DayOfWeek(date = localDate.plusDays(5L)),
            ),
            agendaItems = listOf(
                AgendaItem.Reminder(
                    title = "The title",
                    description = "THe description",
                    remindAt = LocalDateTime.now(),
                    time = LocalDateTime.now(),
                ),

                AgendaItem.Reminder(
                    title = "The title",
                    description = "THe description",
                    remindAt = LocalDateTime.now(),
                    time = LocalDateTime.now(),
                ),
            ),
        ),
    )
}
