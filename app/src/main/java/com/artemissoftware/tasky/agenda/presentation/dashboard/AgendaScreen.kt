package com.artemissoftware.tasky.agenda.presentation.dashboard

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
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.WeekDay
import com.artemissoftware.tasky.agenda.domain.models.DayOfWeek

@Composable
fun AgendaScreen(
    state: AgendaState
) {

    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
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
                                    items = listOf(DayOfWeek("T", "10")),
                                    key = {
                                        it.day
                                    },
                                    itemContent = { item ->

                                        WeekDay(
                                            modifier = Modifier
                                                .size(width = 40.dp, height = 60.dp),
                                            weekDay = "W",
                                            dayOfTheWeek = "13"
                                        )

                                    }
                                )
                            }


                            TaskyText(
                                modifier = Modifier
                                    .padding(vertical = 48.dp),
                                color = Black,
                                style = MaterialTheme.typography.h4,
                                text = stringResource(id = R.string.today)
                            )


                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items(
                                    items = list,
                                    key = {
                                        it.day
                                    },
                                    itemContent = { item ->

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
private fun PAgendaScreenPreview() {
    AgendaScreen(
        state = AgendaState()
    )
}