package com.artemissoftware.tasky.agenda.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Needle(
    color: Color,
    radius: Float,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val screenPixelDensity = context.resources.displayMetrics.density
    val height = (radius * 2 / screenPixelDensity).dp
    val needleHeight = radius / 5

    Canvas(
        modifier = modifier
            .height(height)
            .clipToBounds(),
    ) {
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(x = radius, y = size.height / 2F),
        )
        drawRect(
            color = color,
            size = Size(width = size.width.dp.toPx(), height = needleHeight),
            topLeft = Offset(
                x = 0.dp.toPx(),
                y = (size.height / 2F) - (needleHeight / 2F),
            ),
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun NeedlePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Needle(modifier = Modifier.fillMaxWidth(), radius = 200F, color = Color.Green)
        Needle(radius = 100F, color = Color.Red, modifier = Modifier.width(50.dp))
        Needle(radius = 16F, color = Color.Black, modifier = Modifier.width(50.dp))
    }
}
