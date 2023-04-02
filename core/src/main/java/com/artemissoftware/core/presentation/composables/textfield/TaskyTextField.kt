package com.artemissoftware.core.presentation.composables.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.theme.Black

@Composable
fun TaskyTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = Black,

) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        modifier = modifier.focusRequester(focusRequester),
        textStyle = textStyle,
        value = text,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = textColor,
            cursorColor = Black,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        onValueChange = onValueChange,
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
fun TaskyTextFieldPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        TaskyTextField(
            text = "tasky",
            onValueChange = { },
        )

        TaskyTextField(
            text = "This is a big test for multiple lines on text to be tested in this composable",
            onValueChange = { },
        )
    }
}
