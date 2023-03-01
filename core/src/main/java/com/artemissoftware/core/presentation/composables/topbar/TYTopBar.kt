package com.artemissoftware.core_ui.composables.topbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.composables.text.TYText
import com.artemissoftware.core_ui.theme.Green
import com.artemissoftware.core_ui.theme.White

@Composable
fun TYTopBar(
    toolbarActions: @Composable RowScope.(Color) -> Unit = {},
    contentColor: Color = White,
    backGroundColor: Color = Green,
    onBackClicked: (() -> Unit)? = null,
    title: String? = null
) {

    TopAppBar(
        elevation = 0.dp,
        navigationIcon = {
            onBackClicked?.let {
                TYToolBarAction(
                    iconId = R.drawable.ic_visibility,
                    onClicked = it,
                    tint = contentColor
                )
            }
        },
        title = {
            title?.let {
                TYText(
                    style = MaterialTheme.typography.subtitle1,
                    text = it,
                    color = contentColor,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

        },
        backgroundColor =  backGroundColor,
        actions = {
            toolbarActions(contentColor)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TYTopBarPreview() {

    val text = "EDIT TASK"

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TYTopBar()
        TYTopBar(title = text, onBackClicked = {})
        TYTopBar(
            onBackClicked = {},
            title = text,
            toolbarActions = { color->
                TYToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
            }
        )
        TYTopBar(
            title = text,
            toolbarActions = { color->
                TYToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
            }
        )
        TYTopBar(
            title = text,
            toolbarActions = { color->
                TYToolBarAction(text = "Save")
            }
        )
        TYTopBar(
            title = text,
            toolbarActions = { color->
                TYToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                TYToolBarAction(text = "Save", tint = color)
            }
        )
        TYTopBar(
            title = text,
            toolbarActions = { color->
                TYToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                TYToolBarAction(iconId = R.drawable.ic_visibility)
            }
        )
    }
}