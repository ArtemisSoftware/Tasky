package com.artemissoftware.tasky.agenda.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextField
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditItem
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.authentication.presentation.login.ManageUIEvents
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    editItem: EditItem
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit){
        // TODO: check if with hilt navigation I can get this object directly on the viewmodel and make this call on init
        viewModel.onTriggerEvent(EditEvents.LoadData(editItem))
    }

    EditScreenContent(
        text = viewModel.text,
        state = state,
        events = viewModel::onTriggerEvent,
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onPopBackStack = {
            navigator.popBackStack()
        },
    )
}

@Composable
private fun EditScreenContent(
    state: EditState,
    events: (EditEvents) -> Unit,
    text: String,
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
                toolbarActions = { color ->

                    TaskyToolBarAction(
                        text = stringResource(id = R.string.save),
                        tint = Green,
                        onClicked = {
                            events(EditEvents.Update)
                        },
                    )
                },
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Light,
                )

                Spacer(modifier = Modifier.height(32.dp))

                TaskyTextField(
                    text = text,
                    onValueChange = {
                        events(EditEvents.UpdateText(it))
                    },
                )

                TaskyText(
                    text = text,
                    style = if (state.editType == EditType.Description) MaterialTheme.typography.body2 else MaterialTheme.typography.h6,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun EditScreenContentEditTitlePreview() {
    EditScreenContent(
        state = EditState(
            isLoading = false,
        ),
        events = {},
        text = "viewModel.text",
    )
}

@Preview(showBackground = true)
@Composable
fun EditScreenContentEditDescriptionPreview() {
    EditScreenContent(
        state = EditState(
            isLoading = false,
            editType = EditType.Title,
        ),
        events = {},
        text = "viewModel.text",
    )
}
