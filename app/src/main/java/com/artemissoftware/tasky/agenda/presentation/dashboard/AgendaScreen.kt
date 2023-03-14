package com.artemissoftware.tasky.agenda.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.core.presentation.composables.TaskyAvatar
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.presentation.theme.LightBlue
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.core.util.StringUtil
import com.artemissoftware.core.util.extensions.format
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.WeekDay
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentCard
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.DayOfWeek
import com.artemissoftware.tasky.agenda.presentation.mappers.toAgendaItemType
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun AgendaScreen(
    state: AgendaState
) {

    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
        topBar = {
            TaskyTopBar(
                onBackClicked = {

                },
                backGroundColor = Black,
                toolbarActions = { color->

                    TaskyAvatar(
                        text =  StringUtil.getInitials(state.userName ?: ""),
                        size = 34.dp,
                        circleColor = Light,
                        textColor = LightBlue,
                        modifier = Modifier
                            .clickable {

                            }
                    )
                }
            )
        },
        content = {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TaskyContentSurface(
                    content = {

                        Column(

                        ) {

                            LazyRow(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                                            dayOfTheWeek = item.day
                                        )

                                    }
                                )
                            }


                            TaskyText(
                                modifier = Modifier
                                    .padding(bottom = 20.dp, top = 20.dp),
                                color = Black,
                                style = MaterialTheme.typography.h6,
                                text = if(state.selectedDayOfTheWeek == LocalDate.now()) stringResource(id = R.string.today) else state.selectedDayOfTheWeek.format()
                            )


                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
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
                                            date = item.itemTime.format(),
                                            onCheckedChange = {

                                            },
                                            onOptionClick = {

                                            }
                                        )
                                    }
                                )
                            }

                        }
                    }
                )
            }



        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AgendaScreenPreview() {
    AgendaScreen(
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
                )
            )
        )
    )
}