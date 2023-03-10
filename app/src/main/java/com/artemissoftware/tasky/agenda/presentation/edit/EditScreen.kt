package com.artemissoftware.tasky.agenda.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType

@Composable
fun EditScreen(
    state: EditState,
    text: String,
    events: (EditEvents) -> Unit
) {

    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = White,
        topBar = {
            TaskyTopBar(
                leftIcon = R.drawable.ic_arrow_left,
                contentColor = Black,
                onBackClicked = {
                    events(EditEvents.PopBackStack)
                },
                backGroundColor = White,
                title = stringResource(id = state.editType.title).uppercase(),
                toolbarActions = { color->

                    TaskyToolBarAction(
                        text = stringResource(id = R.string.save),
                        tint = Green,
                        onClicked = {
                            events(EditEvents.Save)
                        }
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Light
                )

                Spacer(modifier = Modifier.height(32.dp))

                TaskyText(
                    text = text,
                    style = if(state.editType == EditType.Description) MaterialTheme.typography.body2 else MaterialTheme.typography.h6
                )

            }

        }
    )
}


@Preview(showBackground = true)
@Composable
fun EditScreenEditTitlePreview() {
    EditScreen(
        state = EditState(
            isLoading = false
        ),
        events = {},
        text = "I am Batman"
    )
}

@Preview(showBackground = true)
@Composable
fun EditScreenEditDescriptionPreview() {
    EditScreen(
        state = EditState(
            isLoading = false,
            editType = EditType.Title
        ),
        events = {},
        text = "And I am the Joker HA HA HA HA"
    )
}