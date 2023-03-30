package com.artemissoftware.tasky.agenda.presentation.detail.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.theme.Light

@Composable
fun DetailDivider(top: Dp, bottom: Dp = 0.dp) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = top, bottom = bottom),
        color = Light,
    )
}
