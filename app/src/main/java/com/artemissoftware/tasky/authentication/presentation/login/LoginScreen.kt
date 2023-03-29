package com.artemissoftware.tasky.authentication.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Link
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.authentication.presentation.login.composables.LoginForm
import com.artemissoftware.tasky.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreenContent(
        state = state,
        events = viewModel::onTriggerEvent,
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        showDialog = {
            state.scaffoldState.showDialog(it)
        },
        onNavigateAndPopCurrent = {
            navigator.navigate(
                route = it.route,
                navOptions = NavOptions.Builder().setPopUpTo(LoginScreenDestination.route, inclusive = true).build(),
            )
        },
        onNavigate = {
            navigator.navigate(route = it.route)
        },
    )
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    events: (LoginEvents) -> Unit,
) {
    TaskyScaffold(
        isLoading = state.isLoading,
        taskyScaffoldState = state.scaffoldState,
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
                    text = stringResource(id = R.string.welcome_back),
                )

                TaskyContentSurface(
                    content = {
                        Box(
                            modifier = Modifier
                                .padding(top = 48.dp)
                                .padding(bottom = 68.dp)
                                .padding(horizontal = 16.dp),
                        ) {
                            LoginForm(
                                modifier = Modifier.align(Alignment.TopCenter),
                                email = state.email,
                                emailValidationStateType = state.emailValidationStateType,
                                password = state.password,
                                passwordValidationStateType = state.passwordValidationStateType,
                                events = events,
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
                                            color = Link,
                                        ),
                                    ) {
                                        append(stringResource(id = R.string.sign_up))
                                    }
                                },
                                onClick = {
                                    events(LoginEvents.SignUp)
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
private fun LoginScreenContentPreview() {
    LoginScreenContent(state = LoginState(), events = {})
}
