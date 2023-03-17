package com.artemissoftware.tasky.authentication.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffoldState
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Link
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.authentication.presentation.login.composables.LoginForm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun LoginScreen(viewModel: LoginViewModel /* TODO : init viewmodel with Hilt when dependency is included on the project */) {

    val state = viewModel.state.collectAsState().value

    LoginScreenContent(
        state = state,
        events = viewModel::onTriggerEvent
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
private fun LoginScreenContent(
    state: LoginState,
    events: (LoginEvents) -> Unit
) {
    TaskyScaffold(
        isLoading = state.isLoading,
        taskyScaffoldState = state.scaffoldState,
        backgroundColor = Black,
        content = {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TaskyText(
                    modifier = Modifier
                        .padding(vertical = 48.dp),
                    color = White,
                    style = MaterialTheme.typography.h4,
                    text = stringResource(id = R.string.welcome_back)
                )


                TaskyContentSurface(
                    content = {
                        Box(
                            modifier = Modifier
                                .padding(top = 48.dp)
                                .padding(bottom = 68.dp)
                                .padding(horizontal = 16.dp)
                        ){

                            LoginForm(
                                modifier = Modifier.align(Alignment.TopCenter),
                                email = state.email,
                                emailValidationStateType = state.emailValidationStateType,
                                password = state.password,
                                passwordValidationStateType = state.passwordValidationStateType,
                                events = events
                            )

                            TaskyTextButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
                                allCaps = true,
                                text = buildAnnotatedString {
                                    append(stringResource(id = R.string.dont_have_an_account))
                                    append(" ")
                                    withStyle(
                                        style = SpanStyle(
                                            color = Link
                                        )
                                    ) {
                                        append(stringResource(id = R.string.sign_up))
                                    }
                                },
                                onClick = {
                                    events(LoginEvents.SignUp)
                                }
                            )
                        }
                    }
                )
            }

        }
    )



}

@Preview(showBackground = true)
@Composable
private fun LoginScreenContentPreview() {
    LoginScreenContent(state = LoginState(), events = {})
}