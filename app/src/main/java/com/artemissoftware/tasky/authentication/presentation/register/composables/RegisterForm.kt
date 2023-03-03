package com.artemissoftware.tasky.authentication.presentation.register.composables

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
fun RegisterForm(
    name: String,
    email: String,
    password: String,
    onNameValueChange: (String) ->Unit,
    onEmailValueChange: (String) ->Unit,
    onPasswordValueChange: (String) ->Unit,
    onRegisterClick: () ->Unit,
    nameValidationStateType: TaskyTextFieldValidationStateType,
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
            hint = stringResource(id = R.string.name),
            onValueChange = onNameValueChange,
            validationState = nameValidationStateType,
            text = name
        )

        TaskyOutlinedTextField(
            modifier = Modifier
                .padding(top = 16.dp)
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
                .padding(top = 72.dp)
                .fillMaxWidth()
                .height(52.dp),
            text = stringResource(id = R.string.get_started),
            onClick = onRegisterClick
        )
    }



}

@Preview(showBackground = true)
@Composable
private fun RegisterFormPreview() {
    RegisterForm(
        name = "name",
        email = "email",
        password = "password",
        onNameValueChange = {},
        onEmailValueChange = {},
        onPasswordValueChange = {},
        onRegisterClick = {},
        nameValidationStateType = TaskyTextFieldValidationStateType.NOT_VALIDATED,
        emailValidationStateType = TaskyTextFieldValidationStateType.VALID,
        passwordValidationStateType = TaskyTextFieldValidationStateType.INVALID,
    )
}