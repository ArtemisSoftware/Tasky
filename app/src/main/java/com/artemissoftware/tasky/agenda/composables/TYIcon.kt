package com.artemissoftware.tasky.agenda.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R

@Composable
fun TYIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    size: Dp = 16.dp,
    color: Color = Color.Black // TODO: usar o do core UI
) { // TODO: mandar para o core UI

    Icon(
        modifier = modifier.size(size),
        painter = painterResource(icon),
        contentDescription = null,
        tint = color
    )
}

@Preview(showBackground = true)
@Composable
private fun TYIconPreview() {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TYIcon(
            modifier = Modifier,
            icon = R.drawable.ic_launcher_foreground,
            color = Color.Green
        )
        TYIcon(
            modifier = Modifier,
            size = 32.dp,
            icon = R.drawable.ic_launcher_foreground
        )
    }



}