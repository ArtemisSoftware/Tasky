package com.artemissoftware.core.presentation.composables.geometric

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TaskySquare(
    color: Color,
    borderColor: Color = color,
    size: Dp = 24.dp,
    modifier: Modifier = Modifier
) {

    val shape = RoundedCornerShape(5.dp)

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(color)
            .border(color = borderColor, width = (0.2).dp, shape = shape)
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskySquarePreview() {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TaskySquare(color = Color.Green, size = 30.dp)
        TaskySquare(color = Color.Green, size = 50.dp, borderColor = Color.Red)
        TaskySquare(color = Color.Green, size = 100.dp)
    }
}