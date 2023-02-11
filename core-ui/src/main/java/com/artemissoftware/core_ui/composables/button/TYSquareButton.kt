package com.artemissoftware.core_ui.composables.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.theme.Black
import com.artemissoftware.core_ui.theme.White

@Composable
fun TYSquareButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    size: Dp = 56.dp,
    iconColor: Color = White,
    backGroundColor: Color = Black,
    onClick: () -> Unit
) {

    IconButton(
        modifier = modifier
            .background(color = backGroundColor, shape = RoundedCornerShape(16.dp))
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
private fun TYSquareButtonPreview() {
    TYSquareButton(icon = R.drawable.ic_add, onClick = {})
}
