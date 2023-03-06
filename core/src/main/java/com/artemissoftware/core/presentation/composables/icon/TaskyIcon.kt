package com.artemissoftware.core.presentation.composables.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.Light2
import com.artemissoftware.core.presentation.theme.Orange

@Composable
fun TaskyIcon(
    @DrawableRes icon: Int,
    size: Dp = 16.dp,
    color: Color = Black,
    modifier: Modifier = Modifier
) {

    Icon(
        modifier = modifier.size(size),
        painter = painterResource(icon),
        contentDescription = null,
        tint = color
    )
}


@Preview(showBackground = true)
@Composable
private fun TaskyIconPreview() {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TaskyIcon(
            modifier = Modifier,
            icon = R.drawable.ic_add,
            color = Color.Green
        )
        TaskyIcon(
            modifier = Modifier,
            size = 32.dp,
            icon = R.drawable.ic_add
        )
    }
}