package com.artemissoftware.core.presentation.composables.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.theme.Black

@Composable
fun TaskyIcon(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    color: Color = Black,
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(icon),
        contentDescription = null,
        tint = color,
    )
}

@Composable
fun TaskyIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    color: Color = Black,
) {
    Icon(
        modifier = modifier.size(size),
        imageVector = icon,
        contentDescription = null,
        tint = color,
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyIconPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TaskyIcon(
            modifier = Modifier,
            icon = R.drawable.ic_add,
            color = Color.Green,
        )
        TaskyIcon(
            modifier = Modifier,
            size = 32.dp,
            icon = R.drawable.ic_add,
        )
        TaskyIcon(
            modifier = Modifier,
            size = 32.dp,
            icon = Icons.Filled.ArrowDropDown,
        )
    }
}
