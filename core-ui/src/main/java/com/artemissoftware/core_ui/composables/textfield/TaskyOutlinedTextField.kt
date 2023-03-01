package com.artemissoftware.core_ui.composables.textfield

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.composables.text.TaskyText
import com.artemissoftware.core_ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@Composable
fun TaskyOutlinedTextField(
    modifier: Modifier = Modifier,
    taskyTextFieldType: TaskyTextFieldType = TaskyTextFieldType.TEXT,
    hint: String,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    maxChar: Int? = null,
    onValueChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    validationState: TaskyTextFieldValidationStateType = TaskyTextFieldValidationStateType.NOT_VALIDATED
) {

    val focusManager = LocalFocusManager.current
    val relocation = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    val textFormatted = maxChar?.let {
        text.take(maxChar)
    } ?: run { text }
    val isPasswordVisible = remember { mutableStateOf(taskyTextFieldType != TaskyTextFieldType.PASSWORD) }
    val shape = RoundedCornerShape(10.dp)


    OutlinedTextField(
        modifier = modifier
            .clip(shape)
            .background(color = Light2)
            .border(
                width = 1.dp,
                color = if (validationState == TaskyTextFieldValidationStateType.INVALID) ErrorRed else Light2,
                shape = shape
            )
            .bringIntoViewRequester(relocation)
            .onFocusEvent {
                if (it.isFocused) scope.launch {
                    delay(300);
                    relocation.bringIntoView()
                }
            },
        value = textFormatted,
        textStyle = textStyle,
        onValueChange = { textValue ->
            onValueChange.invoke(textValue)
        },
        placeholder = {
            TaskyText(
                text = hint,
                style = MaterialTheme.typography.body1,
                color = HintTextColor
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Light2,
            unfocusedBorderColor = if (validationState == TaskyTextFieldValidationStateType.INVALID) ErrorRed else Light2,
            focusedBorderColor = LightBlue,
            cursorColor = Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = taskyTextFieldType.getKeyboardType(),
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        visualTransformation = getVisualTransformation(isPasswordVisible.value),
        singleLine = true,
        trailingIcon = {
            TrailingIcon(
                taskyTextFieldType = taskyTextFieldType,
                isPasswordVisible = isPasswordVisible,
                validationState = validationState
            )
        },
        shape = shape
    )
}


@Composable
private fun TrailingIcon(
    taskyTextFieldType: TaskyTextFieldType,
    isPasswordVisible: MutableState<Boolean>,
    validationState: TaskyTextFieldValidationStateType = TaskyTextFieldValidationStateType.NOT_VALIDATED
) {
    when (taskyTextFieldType) {

        TaskyTextFieldType.PASSWORD -> {

            IconButton(
                onClick = {
                    isPasswordVisible.value = !isPasswordVisible.value
                },
                content = {
                    Icon(
                        painter = painterResource(if (isPasswordVisible.value) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                        contentDescription = ""
                    )
                }
            )
        }
        else -> {

            if (validationState == TaskyTextFieldValidationStateType.VALID) {
                Icon(
                    tint = Green,
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = ""
                )
            }
        }
    }
}

private fun getVisualTransformation(isPasswordVisible: Boolean) = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun TrailingIconPreview() {

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TrailingIcon(
            taskyTextFieldType = TaskyTextFieldType.PASSWORD,
            isPasswordVisible = mutableStateOf(true)
        )

        TrailingIcon(
            taskyTextFieldType = TaskyTextFieldType.PASSWORD,
            isPasswordVisible = mutableStateOf(false)
        )
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun TaskyOutlinedTextFieldPreview() {

    var text by remember {
        mutableStateOf("Test")
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TaskyOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            text = "",
            hint = "Email address"
        )

        TaskyOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            taskyTextFieldType = TaskyTextFieldType.EMAIL,
            text = "email@email.com",
            hint = "Email address",
            validationState = TaskyTextFieldValidationStateType.VALID
        )

        TaskyOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            taskyTextFieldType = TaskyTextFieldType.PASSWORD,
            text = "text",
            hint = "password"
        )

        TaskyOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            taskyTextFieldType = TaskyTextFieldType.EMAIL,
            text = "text",
            hint = "password",
            validationState = TaskyTextFieldValidationStateType.INVALID
        )

        TaskyOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            taskyTextFieldType = TaskyTextFieldType.EMAIL,
            text = text,
            onValueChange = {
                text = it
            },
            hint = "Email address"
        )
    }
}
