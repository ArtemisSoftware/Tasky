package com.artemissoftware.core.presentation.composables.scaffold

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialog
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.composables.loading.TaskyLoading
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black

@Composable
fun TaskyScaffold(
    backgroundColor: Color = White,
    isLoading: Boolean = false,
    @RawRes loadingLottieId: Int = R.raw.loading_lottie,
    taskyScaffoldState: TaskyScaffoldState? = null,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        backgroundColor = backgroundColor,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            topBar.invoke()
        },
        content = content
    )

    TaskyLoading(
        modifier = Modifier.fillMaxSize(),
        isLoading = isLoading,
        lottieId = loadingLottieId
    )

    taskyScaffoldState?.dialog?.value?.let {
        TaskyDialog(
            taskyDialogType = it,
            onDialogDismiss = { taskyScaffoldState.closeDialog() }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldPreview() {

    TaskyScaffold(
        content = {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldLoadingPreview() {

    TaskyScaffold(
        isLoading = true,
        content = {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldDialogPreview() {

    val dialogTypeSuccess = TaskyDialogType.Success(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TaskyDialogOptions.DoubleOption(
            confirmationText = R.string.ok,
            cancelText = R.string.cancel
        )
    )

    val scaffoldState = TaskyScaffoldState()
    scaffoldState.showDialog(dialogTypeSuccess)

    TaskyScaffold(
        taskyScaffoldState = scaffoldState,
        isLoading = false,
        content = {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldTopBarPreview() {

    TaskyScaffold(
        topBar = {
            TaskyTopBar(
                title = "Top bar title",
                toolbarActions = { color->
                    TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                }
            )
        },
        content = {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldWithContentSurfacePreview() {

    TaskyScaffold(
        backgroundColor = Black,
        topBar = {
            TaskyTopBar(
                title = "Top bar title",
                toolbarActions = { color->
                    TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                }
            )
        },
        content = {

            TaskyContentSurface(
                modifier = Modifier.padding(top = 32.dp),
                content = {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Hello World", modifier = Modifier.padding(8.dp))
                    }
                }
            )

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldWithContentLoadingSurfacePreview() {

    TaskyScaffold(
        isLoading = true,
        backgroundColor = Black,
        topBar = {
            TaskyTopBar(
                title = "Top bar title",
                toolbarActions = { color->
                    TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                }
            )
        },
        content = {

            TaskyContentSurface(
                modifier = Modifier.padding(top = 32.dp),
                content = {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Hello World", modifier = Modifier.padding(8.dp))
                    }
                }
            )

        }
    )
}