package com.artemissoftware.tasky.authentication.presentation.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskySquareButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.authentication.presentation.login.ManageUIEvents
import com.artemissoftware.tasky.authentication.presentation.register.composables.RegisterForm

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreenContent(
        state = state,
        events = viewModel::onTriggerEvent,
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        showDialog = {
            state.scaffoldState.showDialog(it)
        },
        onNavigate = {},
        onPopBackStack = {},
    )
}

@Composable
fun RegisterScreenContent(
    state: RegisterState,
    events: (RegisterEvents) -> Unit,
) {
    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TaskyText(
                    modifier = Modifier
                        .padding(vertical = 48.dp),
                    color = White,
                    style = MaterialTheme.typography.h4,
                    text = stringResource(id = R.string.create_your_account),
                )

                TaskyContentSurface(
                    content = {
                        Box(
                            modifier = Modifier
                                .padding(top = 48.dp)
                                .padding(bottom = 68.dp)
                                .padding(horizontal = 16.dp),
                        ) {
                            RegisterForm(
                                modifier = Modifier.align(Alignment.TopCenter),
                                name = state.name,
                                nameValidationStateType = state.nameValidationStateType,
                                email = state.email,
                                emailValidationStateType = state.emailValidationStateType,
                                password = state.password,
                                passwordValidationStateType = state.passwordValidationStateType,
                                events = events,
                            )

                            TaskySquareButton(
                                modifier = Modifier
                                    .align(Alignment.BottomStart),
                                icon = R.drawable.ic_arrow_left,
                                onClick = {
                                    events(RegisterEvents.PopBackStack)
                                },
                            )
                        }
                    },
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenContentPreview() {
    RegisterScreenContent(state = RegisterState(), events = {})
}
