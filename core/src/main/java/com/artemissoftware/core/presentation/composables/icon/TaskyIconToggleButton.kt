package com.artemissoftware.core.presentation.composables.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.theme.Black

@Composable
fun TaskyIconToggleButton(
    @DrawableRes onIcon: Int,
    @DrawableRes offIcon: Int,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    onIconColor: Color = Black,
    offIconColor: Color = onIconColor,
    selected: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    IconToggleButton(
        checked = selected,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(size),
    ) {
        TaskyIcon(
            color = if (selected) onIconColor else offIconColor,
            icon = if (selected) onIcon else offIcon,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyIconToggleButtonPreview() {
    TaskyIconToggleButton(
        onIcon = R.drawable.ic_add,
        offIcon = R.drawable.ic_visibility_off,
        modifier = Modifier,
        onCheckedChange = {},
    )
}
