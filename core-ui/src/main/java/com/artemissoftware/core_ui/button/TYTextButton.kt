package com.artemissoftware.core_ui.button

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.core_ui.text.TYText
import com.artemissoftware.core_ui.theme.Silver

@Composable
fun TYTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: ()->Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        TYText(
            style = MaterialTheme.typography.button,
            text = text,
            color = Silver
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TYTextButtonPreview() {

    TYTextButton(text = "JOIN EVENT", onClick = {})
}