package com.artemissoftware.tasky.agenda.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Needle(
    modifier: Modifier = Modifier,
    color: Color,
    radius: Float
) {

    val context = LocalContext.current
    val screenPixelDensity = context.resources.displayMetrics.density
    val height = (radius *2 / screenPixelDensity).dp
    val needleHeight = radius/5

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(x = radius, y = size.height / 2F)
        )
        drawRect(
            color = color,
            size = Size(width = size.width.dp.toPx(), height = needleHeight),
            topLeft = Offset(
                x = 0.dp.toPx(),
                y = (size.height / 2F) - (needleHeight / 2F)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NeedlePreview() {

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Needle(radius = 200F, color = Color.Green)
        Needle(radius = 100F, color = Color.Red, modifier = Modifier.width(100.dp))
        Needle(radius = 16F, color = Color.Black, modifier = Modifier.width(50.dp))
    }
}