package com.artemissoftware.tasky.agenda.composables

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
fun TYSquare( // TODO: change to core UI
    size: Dp = 24.dp,
    color: Color,
    borderColor: Color = color
) {

    val shape = RoundedCornerShape(2.dp)

    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(color)
            .border(color = borderColor, width = (0.2).dp, shape = shape)
    )
}

@Preview(showBackground = true)
@Composable
private fun TYSquarePreview() {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TYSquare(color = Color.Green, size = 30.dp)
        TYSquare(color = Color.Green, size = 50.dp, borderColor = Color.Red)
        TYSquare(color = Color.Green, size = 100.dp)
    }
}