package com.artemissoftware.tasky.authentication.presentation.login.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.button.TaskyButton
import com.artemissoftware.core.presentation.composables.textfield.TaskyOutlinedTextField
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldType
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.tasky.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginForm(
    email: String,
    password: String,
    onEmailValueChange: (String) ->Unit,
    onPasswordValueChange: (String) ->Unit,
    onLoginClick: () ->Unit,
    emailValidationStateType: TaskyTextFieldValidationStateType,
    passwordValidationStateType: TaskyTextFieldValidationStateType,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        TaskyOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            taskyTextFieldType = TaskyTextFieldType.EMAIL,
            hint = stringResource(id = R.string.email_address),
            onValueChange = onEmailValueChange,
            validationState = emailValidationStateType,
            text = email
        )

        TaskyOutlinedTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(64.dp),
            taskyTextFieldType = TaskyTextFieldType.PASSWORD,
            hint = stringResource(id = R.string.password),
            onValueChange = onPasswordValueChange,
            validationState = passwordValidationStateType,
            text = password,
            imeAction = ImeAction.Done
        )

        TaskyButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(52.dp),
            text = stringResource(id = R.string.log_in),
            onClick = onLoginClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginFormPreview() {
    LoginForm(
        email = "email",
        password = "password",
        onEmailValueChange = {},
        onPasswordValueChange = {},
        onLoginClick = {},
        emailValidationStateType = TaskyTextFieldValidationStateType.VALID,
        passwordValidationStateType = TaskyTextFieldValidationStateType.INVALID,
    )
}