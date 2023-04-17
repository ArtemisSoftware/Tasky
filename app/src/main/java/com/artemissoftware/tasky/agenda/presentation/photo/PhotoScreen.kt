package com.artemissoftware.tasky.agenda.presentation.photo

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.core.presentation.composables.icon.TaskySquareIcon
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.LightBlue
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.ramcosta.composedestinations.annotation.Destination
import com.artemissoftware.core.R as CoreR

@Destination
@Composable
fun PhotoScreen(
    picture: Picture,
) {
    // TODO: complete this on next PR
}

@Composable
private fun PhotoScreenContent(
    picture: Picture,
    events: (PhotoEvents) -> Unit,
) {
    TaskyScaffold(
        backgroundColor = Black,
        topBar = {
            TaskyTopBar(
                onBackClicked = {
                    events(PhotoEvents.PopBackStack)
                },
                backGroundColor = Black,
                title = stringResource(id = R.string.photo),
                toolbarActions = { color ->
                    TaskyToolBarAction(
                        iconId = R.drawable.ic_trash,
                        tint = color,
                        onClicked = {
                            events(PhotoEvents.DeletePhoto("")) // TODO: add photo ID
                        },
                    )
                },
            )
        },
        content = {

            val source: Any = when(picture){
                is Picture.Local -> Uri.parse(picture.uri)
                is Picture.Remote -> picture.url
            }
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(source)
                    .size(Size.ORIGINAL)
                    .crossfade(800)
                    .error(CoreR.drawable.ic_tasky_logo)
                    .placeholder(CoreR.drawable.ic_tasky_logo)
                    .build(),
            )
            when (painter.state) {
                is AsyncImagePainter.State.Error -> {
                    TaskySquareIcon(
                        modifier = Modifier,
                        size = 32.dp,
                        borderWidth = 2.dp,
                        borderColor = LightBlue,
                        icon = CoreR.drawable.ic_tasky_logo,
                    )
                }

                is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier,
                        painter = painter,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )
                }
                else -> Unit
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoScreenPreview() {
//    PhotoScreen(
//        picture =
//    )
}
