package com.artemissoftware.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.util.StringUtil

@Composable
fun TaskyAvatar(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Black,
    circleColor: Color = Green,
    size: Dp = 45.dp,
    textStyle: TextStyle = MaterialTheme.typography.button,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color = circleColor),
        contentAlignment = Alignment.Center,
    ) {
        TaskyText(
            text = StringUtil.getInitials(text),
            style = textStyle,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyAvatarPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        TaskyAvatar(
            text = "AA",
        )

        TaskyAvatar(
            text = "WW",
            size = 32.dp,
        )
    }
}
