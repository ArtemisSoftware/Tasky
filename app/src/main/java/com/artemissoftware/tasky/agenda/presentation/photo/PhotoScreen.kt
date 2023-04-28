package com.artemissoftware.tasky.agenda.presentation.photo

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.core.presentation.composables.connectivity.connectivityState
import com.artemissoftware.core.presentation.composables.connectivity.models.TaskyConnectionState
import com.artemissoftware.core.presentation.composables.icon.TaskySquareIcon
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.LightBlue
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.artemissoftware.tasky.agenda.presentation.edit.models.PictureRecipient
import com.artemissoftware.tasky.authentication.presentation.login.ManageUIEvents
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.artemissoftware.core.R as CoreR

@Destination
@Composable
fun PhotoScreen(
    viewModel: PhotoViewModel = hiltViewModel(),
    picture: Picture,
    isEventCreator: Boolean,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<PictureRecipient>,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val connection by connectivityState()

    PhotoScreenContent(
        picture = picture,
        isEventCreator = isEventCreator,
        state = state,
        isNetworkConnectionAvailable = (connection === TaskyConnectionState.Available),
        events = viewModel::onTriggerEvent,
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onPopBackStack = {
            navigator.popBackStack()
        },
        onPopBackStackWithArguments = {
            resultNavigator.navigateBack(it.arguments as PictureRecipient)
        },
    )
}

@Composable
private fun PhotoScreenContent(
    picture: Picture,
    isEventCreator: Boolean,
    isNetworkConnectionAvailable: Boolean,
    events: (PhotoEvents) -> Unit,
    state: PhotoState,
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
                toolbarActions = { color ->
                    if(isEventCreator && isNetworkConnectionAvailable) {
                        TaskyToolBarAction(
                            iconId = R.drawable.ic_trash,
                            tint = color,
                            onClicked = {
                                events(PhotoEvents.DeletePhoto(picture.id))
                            },
                        )
                    }
                },
            )
        },
        content = {
            val source: Any = when (picture) {
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

            Box(modifier = Modifier.fillMaxSize()) {
                when (painter.state) {
                    is AsyncImagePainter.State.Error -> {
                        TaskySquareIcon(
                            modifier = Modifier.align(Alignment.Center),
                            size = 32.dp,
                            borderWidth = 2.dp,
                            borderColor = LightBlue,
                            icon = CoreR.drawable.ic_tasky_logo,
                        )
                    }

                    is AsyncImagePainter.State.Success -> {
                        Image(
                            modifier = Modifier.align(Alignment.Center),
                            painter = painter,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                        )
                    }
                    else -> Unit
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoScreenContentPreview() {
    PhotoScreenContent(
        picture = Picture.Local(picId = "123", uri = "http://www.batman.com/riddler.jpg"),
        events = {},
        isNetworkConnectionAvailable = true,
        isEventCreator = true,
        state = PhotoState(),
    )
}
