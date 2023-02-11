package com.artemissoftware.core_ui.textfield

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.text.TYText
import com.artemissoftware.core_ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@Composable
fun TYOutlinedTextField(
    modifier: Modifier = Modifier,
    tyTextFieldType: TYTextFieldType = TYTextFieldType.TEXT,
    hint: String,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    maxChar: Int? = null,
    onValueChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    validationState: TYTextFieldValidationStateType = TYTextFieldValidationStateType.NOT_VALIDATED
) {

    val focusManager = LocalFocusManager.current
    val relocation = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    val textFormatted = text.take(maxChar ?: tyTextFieldType.getMaxChar())
    val isPasswordVisible = remember { mutableStateOf(tyTextFieldType != TYTextFieldType.PASSWORD) }
    val shape = RoundedCornerShape(10.dp)


    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(shape)
            .background(color = Light2)
            .border(
                width = 1.dp,
                color = if (validationState == TYTextFieldValidationStateType.INVALID) ErrorRed else Light2,
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
            TYText(
                text = hint,
                style = MaterialTheme.typography.body1,
                color = HintTextColor
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Light2,
            unfocusedBorderColor = if (validationState == TYTextFieldValidationStateType.INVALID) ErrorRed else Light2,
            focusedBorderColor = LightBlue,
            cursorColor = Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = tyTextFieldType.getKeyboardType(),
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
                tyTextFieldType = tyTextFieldType,
                onClick = { onValueChange.invoke(text) },
                text = text,
                isPasswordVisible = isPasswordVisible,
                validationState = validationState
            )
        },
        shape = shape
    )
}


@Composable
private fun TrailingIcon(
    tyTextFieldType: TYTextFieldType,
    onClick: (TextFieldValue) -> Unit,
    text: String,
    isPasswordVisible: MutableState<Boolean>,
    validationState: TYTextFieldValidationStateType = TYTextFieldValidationStateType.NOT_VALIDATED
) {
    when (tyTextFieldType) {

        TYTextFieldType.PASSWORD -> {

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

            if (text.isNotBlank() && validationState == TYTextFieldValidationStateType.VALID) {
                IconButton(
                    onClick = {
                        onClick.invoke(TextFieldValue(""))
                    },
                    content = {
                        Icon(
                            tint = Green,
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = ""
                        )
                    }
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
            tyTextFieldType = TYTextFieldType.PASSWORD,
            onClick = {},
            text = "Example",
            isPasswordVisible = mutableStateOf(true)
        )

        TrailingIcon(
            tyTextFieldType = TYTextFieldType.PASSWORD,
            onClick = {},
            text = "Example",
            isPasswordVisible = mutableStateOf(false)
        )
    }
}



@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun TYOutlinedTextFieldPreview() {

    var text by remember {
        mutableStateOf("Test")
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TYOutlinedTextField(
            text = "",
            hint = "Email address"
        )

        TYOutlinedTextField(
            tyTextFieldType = TYTextFieldType.EMAIL,
            text = "email@email.com",
            hint = "Email address",
            validationState = TYTextFieldValidationStateType.VALID
        )

        TYOutlinedTextField(
            tyTextFieldType = TYTextFieldType.PASSWORD,
            text = "text",
            hint = "password"
        )

        TYOutlinedTextField(
            tyTextFieldType = TYTextFieldType.EMAIL,
            text = "text",
            hint = "password",
            validationState = TYTextFieldValidationStateType.INVALID
        )

        TYOutlinedTextField(
            tyTextFieldType = TYTextFieldType.EMAIL,
            text = text,
            onValueChange = {
                text = it
            },
            hint = "Email address"
        )
    }
}
