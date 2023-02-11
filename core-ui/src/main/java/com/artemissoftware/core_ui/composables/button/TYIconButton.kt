package com.artemissoftware.core_ui.composables.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.theme.Light2
import com.artemissoftware.core_ui.theme.LightBlue

@Composable
fun TYIconButton(
    modifier: Modifier = Modifier,
    icon: TYIconButtonType,
    size: Dp = 56.dp,
    borderColor: Color = LightBlue,
    iconColor: Color = LightBlue,
    backGroundColor: Color = Light2,
    onClick: () -> Unit
) {

    val shape = RoundedCornerShape(5.dp)

    IconButton(
        modifier = modifier
            .border(
                width = (0.5).dp,
                color = borderColor,
                shape = shape
            )
            .background(
                color = backGroundColor,
                shape = shape
            )
            .size(size)
            .clip(shape),
        onClick = onClick
    ) {

        when(icon){
            is TYIconButtonType.Drawable -> {
                Icon(
                    painter = painterResource(id = icon.iconId),
                    tint = iconColor,
                    contentDescription = ""
                )
            }
            is TYIconButtonType.ImageUrl -> {
                Image(
                    painterResource(R.drawable.ic_visibility), //TODO: coil image load
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }


    }
}



@Preview(showBackground = true)
@Composable
private fun TYIconPreview() {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TYIconButton(icon = TYIconButtonType.Drawable(R.drawable.ic_add), onClick = {}, size = 35.dp)
        TYIconButton(icon = TYIconButtonType.Drawable(R.drawable.ic_add), onClick = {}, size = 30.dp)
        TYIconButton(icon = TYIconButtonType.Drawable(R.drawable.ic_add), onClick = {}, size = 60.dp)


        TYIconButton(icon = TYIconButtonType.Drawable(R.drawable.ic_visibility), onClick = {}, size = 30.dp)

        TYIconButton(icon = TYIconButtonType.ImageUrl("https://i.pinimg.com/200x150/5f/34/00/5f340004b05809fe2c3bd164ce64c3c8.jpg"), onClick = {}, size = 30.dp)
    }
}