package com.artemissoftware.core.presentation.composables.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.theme.*

@Composable
fun TaskyTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = Black,

) {
    TextField(
        modifier = modifier,
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
