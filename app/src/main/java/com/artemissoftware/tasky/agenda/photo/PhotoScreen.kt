package com.artemissoftware.tasky.agenda.photo

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
import com.artemissoftware.core.R as CoreR

@Composable
fun PhotoScreen(
    state: PhotoState,
    events: (PhotoEvents) -> Unit,
) {

    TaskyScaffold(
        isLoading = state.isLoading,
        backgroundColor = Black,
        topBar = {
            TaskyTopBar(
                onBackClicked = {
                    events(PhotoEvents.PopBackStack)
                },
                backGroundColor = Black,
                title = stringResource(id = R.string.photo),
                toolbarActions = { color->
                    TaskyToolBarAction(
                        iconId = R.drawable.ic_trash,
                        tint = color,
                        onClicked = {
                            events(PhotoEvents.DeletePhoto(""))
                        }
                    )
                }
            )
        },
        content = {

            state.photo?.let {

                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it.url)
                        .size(Size.ORIGINAL)
                        .crossfade(800)
                        .error(CoreR.drawable.ic_tasky_logo)
                        .placeholder(CoreR.drawable.ic_tasky_logo)
                        .build()
                )

                when(painter.state){

                    is AsyncImagePainter.State.Error ->{
                        TaskySquareIcon(
                            modifier = Modifier,
                            size = 32.dp,
                            borderWidth = 2.dp,
                            borderColor = LightBlue,
                            icon = CoreR.drawable.ic_tasky_logo
                        )
                    }

                    is AsyncImagePainter.State.Success ->{
                        Image(
                            modifier = Modifier,
                            painter = painter,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                        )
                    }
                    else ->{}
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoScreenPreview() {
    PhotoScreen(
        state = PhotoState(),
        events = {}
    )
}