package com.artemissoftware.core_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core_ui.button.TYTextButton
import com.artemissoftware.core_ui.text.TYText
import com.artemissoftware.core_ui.theme.Black
import com.artemissoftware.core_ui.theme.Gray
import com.artemissoftware.core_ui.theme.Green

@Composable
fun CircleIdentifier(
    text: String,
    textColor: Color = Black,
    circleColor: Color = Green,
    size: Dp = 45.dp,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color = circleColor),
        contentAlignment = Alignment.Center
    ) {

        TYText(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TYTextButtonPreview() {

    CircleIdentifier(
        text = "AA"
    )
}