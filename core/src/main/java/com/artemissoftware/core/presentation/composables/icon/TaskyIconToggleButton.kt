package com.artemissoftware.core.presentation.composables.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R


@Composable
fun TaskyIconToggleButton(
    @DrawableRes onIcon: Int,
    @DrawableRes offIcon: Int,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
) {

    IconToggleButton(
        checked = selected,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(24.dp)
    ) {
        TaskyIcon(
            icon = if (selected) onIcon else  offIcon
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
        onCheckedChange = {}
    )
}