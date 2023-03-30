package com.artemissoftware.tasky.agenda.presentation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextField
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import com.artemissoftware.tasky.authentication.presentation.login.ManageUIEvents
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination
@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Pair<EditType, String>>,
    text: String,
    editType: EditType,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        // TODO: check if with hilt navigation I can get this object directly on the viewmodel and make this call on init
        viewModel.onTriggerEvent(EditEvents.LoadData(text = text, editType = editType))
    }

    EditScreenContent(
        state = state,
        events = viewModel::onTriggerEvent,
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onPopBackStack = {
            navigator.popBackStack()
        },
        onPopBackStackWithArguments = {
            resultNavigator.navigateBack(it.arguments as Pair<EditType, String>)
        },
    )
}

@Composable
private fun EditScreenContent(
    state: EditState,
    events: (EditEvents) -> Unit,
) {
    TaskyScaffold(
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
                    text = state.text,
                    textStyle = if (state.editType == EditType.Description) MaterialTheme.typography.body2 else MaterialTheme.typography.h6,
                    onValueChange = {
                        events(EditEvents.UpdateText(it))
                    },
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
            text = "viewModel.text",
        ),
        events = {},
    )
}

@Preview(showBackground = true)
@Composable
fun EditScreenContentEditDescriptionPreview() {
    EditScreenContent(
        state = EditState(
            text = "viewModel.text",
            editType = EditType.Title,
        ),
        events = {},
    )
}
