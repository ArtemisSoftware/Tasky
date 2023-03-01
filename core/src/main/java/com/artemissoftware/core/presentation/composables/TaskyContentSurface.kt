package com.artemissoftware.core.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskyContentSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {
        content()

    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyContentSurfacePreview() {
    TaskyContentSurface(
        modifier = Modifier,
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Hello World", modifier = Modifier.padding(8.dp))
            }
        }
    )
}