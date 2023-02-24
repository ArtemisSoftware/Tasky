package com.artemissoftware.core_ui.composables.scaffold

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.composables.dialog.TYDialog
import com.artemissoftware.core_ui.composables.dialog.TYDialogOptions
import com.artemissoftware.core_ui.composables.dialog.TYDialogType
import com.artemissoftware.core_ui.composables.loading.TYLoading
import com.artemissoftware.core_ui.composables.topbar.TYToolBarAction
import com.artemissoftware.core_ui.composables.topbar.TYTopBar

@Composable
fun TYScaffold(
    modifier: Modifier = Modifier,
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