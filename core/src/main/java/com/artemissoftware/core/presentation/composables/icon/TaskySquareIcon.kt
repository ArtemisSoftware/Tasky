package com.artemissoftware.core.presentation.composables.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.artemissoftware.core.presentation.theme.Light2


@Composable
fun TaskySquareIcon(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    padding: Dp = 4.dp,
    backgroundColor: Color = Light2,
    borderColor: Color = backgroundColor,
    iconColor: Color = Black
) {

    val shape = RoundedCornerShape(5.dp)


    Icon(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(backgroundColor)
            .border(
                color = borderColor,
                width = (0.2).dp
            )
            .padding(padding),
        painter = painterResource(icon),
        contentDescription = null,
        tint = iconColor
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskySquareIconPreview() {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

        TaskySquareIcon(
            modifier = Modifier,
            size = 32.dp,
            icon = R.drawable.ic_add
        )

        TaskySquareIcon(
            modifier = Modifier,
            size = 32.dp,
            icon = R.drawable.ic_add
        )
    }
}