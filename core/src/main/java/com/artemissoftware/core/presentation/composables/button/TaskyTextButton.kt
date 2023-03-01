package com.artemissoftware.core.presentation.composables.button

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Silver

@Composable
fun TaskyTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: ()->Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        TaskyText(
            style = MaterialTheme.typography.button,
            text = text,
            color = Silver
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyTextButtonPreview() {

    TaskyTextButton(text = "JOIN EVENT", onClick = {})
}