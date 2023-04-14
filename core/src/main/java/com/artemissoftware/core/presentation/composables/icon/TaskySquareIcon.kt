package com.artemissoftware.core.presentation.composables.icon

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
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
    borderWidth: Dp = (0.2).dp,
    borderColor: Color = backgroundColor,
    iconColor: Color = Black,
) {
    val shape = RoundedCornerShape(5.dp)

    Icon(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(backgroundColor)
            .border(
                color = borderColor,
                width = borderWidth,
                shape = shape,
            )
            .padding(padding),
        painter = painterResource(icon),
        contentDescription = null,
        tint = iconColor,
    )
}

@Composable
fun TaskySquareIcon(
    source: String,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    padding: Dp = 4.dp,
    backgroundColor: Color = Light2,
    borderWidth: Dp = (0.2).dp,
    borderColor: Color = backgroundColor,
    iconColor: Color = Black,
) {
    val shape = RoundedCornerShape(5.dp)

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(source)
            .size(Size.ORIGINAL)
            .crossfade(800)
            .error(R.drawable.ic_tasky_logo)
            .placeholder(R.drawable.ic_tasky_logo)
            .build(),
    )

    val state = painter.state
    if (state is AsyncImagePainter.State.Error) {
        Icon(
            modifier = modifier
                .size(size)
                .clip(shape)
                .background(backgroundColor)
                .border(
                    color = borderColor,
                    width = borderWidth,
                    shape = shape,
                )
                .padding(padding),
            painter = painterResource(R.drawable.ic_tasky_logo),
            contentDescription = null,
            tint = iconColor,
        )
    } else if (state is AsyncImagePainter.State.Success) {
        Image(
            modifier = modifier
                .size(size)
                .clip(shape)
                .background(backgroundColor)
                .border(
                    color = borderColor,
                    width = borderWidth,
                    shape = shape,
                ),
            painter = painter,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun TaskySquareIcon(
    source: Uri,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    padding: Dp = 4.dp,
    backgroundColor: Color = Light2,
    borderWidth: Dp = (0.2).dp,
    borderColor: Color = backgroundColor,
    iconColor: Color = Black,
) {
    val shape = RoundedCornerShape(5.dp)

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(source)
            .size(Size.ORIGINAL)
            .crossfade(800)
            .error(R.drawable.ic_tasky_logo)
            .placeholder(R.drawable.ic_tasky_logo)
            .build(),
    )

    val state = painter.state
    if (state is AsyncImagePainter.State.Error) {
        Icon(
            modifier = modifier
                .size(size)
                .clip(shape)
                .background(backgroundColor)
                .border(
                    color = borderColor,
                    width = borderWidth,
                    shape = shape,
                )
                .padding(padding),
            painter = painterResource(R.drawable.ic_tasky_logo),
            contentDescription = null,
            tint = iconColor,
        )
    } else if (state is AsyncImagePainter.State.Success) {
        Image(
            modifier = modifier
                .size(size)
                .clip(shape)
                .background(backgroundColor)
                .border(
                    color = borderColor,
                    width = borderWidth,
                    shape = shape,
                ),
            painter = painter,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskySquareIconPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TaskySquareIcon(
            modifier = Modifier,
            size = 32.dp,
            icon = R.drawable.ic_add,
        )

        TaskySquareIcon(
            modifier = Modifier,
            size = 32.dp,
            borderWidth = 2.dp,
            borderColor = Black,
            icon = R.drawable.ic_add,
        )

        TaskySquareIcon(
            modifier = Modifier,
            size = 32.dp,
            borderWidth = 2.dp,
            borderColor = Black,
            source = "https://filmschoolrejects.com/wp-content/uploads/2022/02/The-Batman-Best-Comics.jpg",
        )
    }
}
