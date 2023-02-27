package com.artemissoftware.tasky.agenda.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R

@Composable
fun EventRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
) {

    IconToggleButton(
        checked = selected,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(24.dp)
    ) {
        TYIcon(
            icon = if (selected) {
                    R.drawable.ic_launcher_foreground
                } else {
                    R.drawable.ic_launcher_background
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EventRadioButtonPreview() {
    EventRadioButton(
        modifier = Modifier,
        onCheckedChange = {}
    )
}