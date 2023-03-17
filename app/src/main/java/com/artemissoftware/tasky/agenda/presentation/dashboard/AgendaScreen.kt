package com.artemissoftware.tasky.agenda.presentation.dashboard

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyAvatar
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.TaskyDropDown
import com.artemissoftware.core.presentation.composables.dropdown.TaskyDropDownItem
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.LightBlue
import com.artemissoftware.core.util.DateTimePatternsConstants
import com.artemissoftware.core.util.extensions.format
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.WeekDay
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentCard
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.DayOfWeek
import com.artemissoftware.tasky.agenda.presentation.dashboard.composables.AgendaTopBar
import com.artemissoftware.tasky.agenda.presentation.mappers.toAgendaItemType
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun AgendaScreen(
    state: AgendaState,
    events: (AgendaEvents) -> Unit,
) {
    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
        topBar = {
            AgendaTopBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                backGroundColor = Black,
                toolbarActionsLeft = {
                    TaskyDropDown<String>(
                        options = listOf("Tasky 1", "Tasky 2", "Tasky 3"),
                        defaultOption = "priority",
                        onOptionSelected = {},
                        addDivider = true,
                        modifier = Modifier.width(140.dp),
                        menuOption = {
                            TaskyDropDownItem(text = it)
                        },
                    )
                },
                toolbarActions = {
                    TaskyAvatar(text = "BW", circleColor = LightBlue)
                },
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TaskyContentSurface(
                    content = {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                        ) {
                            LazyRow(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
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
                                            onClick = {
                                                events(AgendaEvents.ChangeDate(item.date))
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
                                            agendaItemType = item.toAgendaItemType(),
                                            title = item.itemTitle,
                                            description = item.itemDescription,
                                            date = item.itemTime.format(DateTimePatternsConstants.DATE_TIME_PATTERN_MMM_d_HH_mm),
                                            onCheckedChange = {
                                                events(AgendaEvents.CompleteAssignment(item.itemId))
                                            },
                                            onOptionClick = {
                                                // TODO : show options
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

@Preview(showBackground = true)
@Composable
private fun AgendaScreenPreview() {
    AgendaScreen(
        events = {},
        state = AgendaState(
            userName = "Bruce Wayne",
            daysOfTheWeek = listOf(
                DayOfWeek(date = LocalDate.now()),
                DayOfWeek(date = LocalDate.now().plusDays(1L)),
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
