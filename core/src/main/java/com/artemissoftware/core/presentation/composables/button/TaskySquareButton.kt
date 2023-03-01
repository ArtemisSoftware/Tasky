package com.artemissoftware.core.presentation.composables.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.White

@Composable
fun TaskySquareButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    size: Dp = 56.dp,
    iconColor: Color = White,
    backgroundColor: Color = Black,
    onClick: () -> Unit
) {

    IconButton(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .size(size),
        onClick = onClick
    ){
        Icon(
            painter = painterResource(id = icon),
            tint = iconColor,
            modifier = Modifier,
            contentDescription = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskySquareButtonPreview() {
    TaskySquareButton(icon = R.drawable.ic_add, onClick = {})
}
