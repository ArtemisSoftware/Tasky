package com.artemissoftware.core.presentation.composables.scaffold

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialog
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.composables.loading.TaskyLoading
import com.artemissoftware.core.presentation.composables.snackbar.TaskySnackBar
import com.artemissoftware.core.presentation.composables.snackbar.TaskySnackBarType
import com.artemissoftware.core.presentation.composables.topbar.TaskyToolBarAction
import com.artemissoftware.core.presentation.composables.topbar.TaskyTopBar
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.core.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun TaskyScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    isLoading: Boolean = false,
    @RawRes loadingLottieId: Int = R.raw.loading_lottie,
    taskyScaffoldState: TaskyScaffoldState? = null,
    fabPosition: FabPosition = FabPosition.End,
    floatingActionButton: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            backgroundColor = backgroundColor,
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                topBar.invoke()
            },
            floatingActionButtonPosition = fabPosition,
            floatingActionButton = {
                floatingActionButton.invoke()
            },
            content = content,
        )

        TaskyLoading(
            modifier = Modifier.fillMaxSize(),
            isLoading = isLoading,
            lottieId = loadingLottieId,
        )

        taskyScaffoldState?.let {
            it.dialog.value?.let {
                TaskyDialog(
                    taskyDialogType = it,
                    onDialogDismiss = { taskyScaffoldState.closeDialog() },
                )
            }

            it.taskySnackBarState.snackbar.value?.let { taskySnackBarType ->
                TaskySnackBar(
                    taskySnackBarType = taskySnackBarType,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                )

                coroutineScope.launch {
                    delay((1.2).seconds)
                    taskyScaffoldState.taskySnackBarState.close()
                }
            }
        }
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
        },
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
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldDialogPreview() {
    val dialogTypeSuccess = TaskyDialogType.Success(
        title = UiText.DynamicString("Get updates"),
        description = UiText.DynamicString("Allow permission to send notifications every day of the year"),
        dialogOptions = TaskyDialogOptions.DoubleOption(
            confirmationText = UiText.StringResource(R.string.ok),
            cancelText = UiText.StringResource(R.string.cancel),
        ),
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
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldTopBarPreview() {
    TaskyScaffold(
        topBar = {
            TaskyTopBar(
                title = "Title",
                toolbarActions = { color ->
                    TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                    TaskyToolBarAction(iconId = R.drawable.ic_visibility)
                },
            )
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }
        },
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
                toolbarActions = { color ->
                    TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                },
            )
        },
        content = {
            TaskyContentSurface(
                modifier = Modifier.padding(top = 32.dp),
                content = {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Hello World", modifier = Modifier.padding(8.dp))
                    }
                },
            )
        },
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
                toolbarActions = { color ->
                    TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                },
            )
        },
        content = {
            TaskyContentSurface(
                modifier = Modifier.padding(top = 32.dp),
                content = {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Hello World", modifier = Modifier.padding(8.dp))
                    }
                },
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskyScaffoldSnackBarPreview() {
    val example = TaskySnackBarType.Info(
        text = UiText.DynamicString("Get updates"),
        imageVector = Icons.Default.Info,
    )

    val scaffoldState = TaskyScaffoldState()
    scaffoldState.taskySnackBarState.show(example)

    TaskyScaffold(
        taskyScaffoldState = scaffoldState,
        isLoading = false,
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }
        },
    )
}
