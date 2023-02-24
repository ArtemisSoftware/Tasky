package com.artemissoftware.core_ui.composables.scaffold

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.composables.TYSurface
import com.artemissoftware.core_ui.composables.dialog.TYDialog
import com.artemissoftware.core_ui.composables.dialog.TYDialogOptions
import com.artemissoftware.core_ui.composables.dialog.TYDialogType
import com.artemissoftware.core_ui.composables.loading.TYLoading
import com.artemissoftware.core_ui.composables.topbar.TYToolBarAction
import com.artemissoftware.core_ui.composables.topbar.TYTopBar
import com.artemissoftware.core_ui.theme.White

@Composable
fun TYScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    isLoading: Boolean = false,
    @RawRes loadingLottieId: Int = R.raw.loading_lottie,
    tyScaffoldState: TYScaffoldState? = null,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
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

        TYLoading(
            isLoading = isLoading,
            lottieId = loadingLottieId
        )

        tyScaffoldState?.let { TYDialog(tyScaffoldState = it) }

    }
    
}

@Preview(showBackground = true)
@Composable
private fun FGScaffold_1_Preview() {

    TYScaffold(
        content = {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun FGScaffold_2_Preview() {

    TYScaffold(
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
private fun FGScaffold_3_Preview() {

    val dialogTypeSuccess = TYDialogType.Success(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TYDialogOptions(
            confirmationTextId = R.string.ok,
            cancelTextId = R.string.cancel
        )
    )

    val scaffoldState = TYScaffoldState()
    scaffoldState.showDialog(dialogTypeSuccess)

    TYScaffold(
        tyScaffoldState = scaffoldState,
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
private fun FGScaffold_4_Preview() {

    TYScaffold(
        topBar = {
            TYTopBar(
                title = "Top bar title",
                toolbarActions = { color->
                    TYToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
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
private fun FGScaffold_5_Preview() {

    TYScaffold(
        topBar = {
            TYTopBar(
                title = "Top bar title",
                toolbarActions = { color->
                    TYToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                }
            )
        },
        content = {

            TYSurface(
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