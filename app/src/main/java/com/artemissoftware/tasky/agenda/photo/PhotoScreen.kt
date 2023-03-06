package com.artemissoftware.tasky.agenda.photo

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.tasky.R

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
                        iconId = R.drawable.ic_launcher_background,
                        tint = color,
                        onClicked = {
                            events(PhotoEvents.DeletePhoto(""))
                        }
                    )
                }
            )
        },
        content = {





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