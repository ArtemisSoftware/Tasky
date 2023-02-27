package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TYDivider( // TODO: mudar a cor e por no core UI
    modifier: Modifier = Modifier,
    color: Color = Color.Red
) {
    Divider(
        modifier = modifier,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
private fun TYDividerPreview() {
    TYDivider(modifier = Modifier.fillMaxWidth())

}