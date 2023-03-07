package com.artemissoftware.tasky.agenda.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyButton
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.composables.textfield.TaskyOutlinedTextField
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldType
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.presentation.theme.Link
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.Greeting
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentDescription
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentHeader
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentNotification
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentTime
import com.artemissoftware.tasky.ui.theme.TaskyTheme

@Composable
fun DetailScreen(
    state: DetailState
) {

    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
        content = {

            TaskyContentSurface(

                content = {

                    state.agendaItemType?.let{ item->

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
                                    agendaItemType = item,
                                    title = "First title",
                                    modifier = Modifier.fillMaxWidth(),
                                    isEditing = true,
                                )

                                TaskyDivider(top = 20.dp, bottom = 20.dp)

                                AssignmentDescription(
                                    isEditing = true,
                                    description = "Second description of a really long one to prove that size is important for the space available",
                                    modifier = Modifier.fillMaxWidth()
                                )

                                TaskyDivider(top = 20.dp, bottom = 28.dp)

                                AssignmentTime(
                                    title = R.string.from,
                                    day = "Jul 21 2022",
                                    hour = "08:00",
                                    modifier = Modifier.fillMaxWidth()
                                )

                                TaskyDivider(top = 20.dp, bottom = 20.dp)

                                AssignmentNotification(
                                    description = "First description",
                                    modifier = Modifier.fillMaxWidth()
                                )

                            }



                            Column(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                TaskyDivider(top = 20.dp, bottom = 20.dp)
                                TaskyTextButton(text = stringResource(id = item.title), onClick = {})
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

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        state = DetailState(
            agendaItemType = AgendaItemType.Reminder()
        )
    )
}