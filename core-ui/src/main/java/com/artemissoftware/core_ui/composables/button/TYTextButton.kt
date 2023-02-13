package com.artemissoftware.core_ui.composables.button

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.core_ui.composables.text.TYText
import com.artemissoftware.core_ui.theme.Silver

@Composable
fun TYTextButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Silver,
    onClick: ()->Unit,
    style: TextStyle = MaterialTheme.typography.button,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        TYText(
            style = style,
            text = text,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TYTextButtonPreview() {

    TYTextButton(text = "JOIN EVENT", onClick = {})
}