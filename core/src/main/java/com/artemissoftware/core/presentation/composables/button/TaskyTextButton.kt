package com.artemissoftware.core.presentation.composables.button

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Silver

@Composable
fun TaskyTextButton(
    text: String,
    onClick: ()->Unit,
    allCaps: Boolean = true,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.button,
    color: Color = Silver
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        TaskyText(
            allCaps = allCaps,
            style = style,
            text = text,
            color = color
        )
    }
}

@Composable
fun TaskyTextButton(
    text: AnnotatedString,
    onClick: ()->Unit,
    allCaps: Boolean = true,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.button,
    color: Color = Silver
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        TaskyText(
            allCaps = allCaps,
            style = style,
            text = text,
            color = color
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun TaskyTextButtonPreview() {

    TaskyTextButton(text = "JOIN EVENT", onClick = {})
}