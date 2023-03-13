package com.artemissoftware.core.presentation.composables.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.White

@Composable
fun TaskyTopBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int = R.drawable.ic_close,
    toolbarActions: @Composable RowScope.(Color) -> Unit = {},
    contentColor: Color = White,
    backGroundColor: Color = Green,
    onBackClicked: (() -> Unit)? = null,
    allCaps: Boolean = false,
    title: String? = null
) {

    TopAppBar(
        modifier = modifier,
        elevation = 0.dp,
        navigationIcon = {
            onBackClicked?.let {
                TaskyToolBarAction(
                    iconId = leftIcon,
                    onClicked = it,
                    tint = contentColor
                )
            }
        },
        title = {
            title?.let {
                TaskyText(
                    style = MaterialTheme.typography.subtitle1,
                    text = it,
                    allCaps = allCaps,
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
private fun TaskyTopBarPreview() {

    val text = "EDIT TASK"

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TaskyTopBar()
        TaskyTopBar(title = text, onBackClicked = {})
        TaskyTopBar(
            onBackClicked = {},
            title = text,
            toolbarActions = { color->
                TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
            }
        )
        TaskyTopBar(
            title = text,
            toolbarActions = { color->
                TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
            }
        )
        TaskyTopBar(
            title = text,
            toolbarActions = { color->
                TaskyToolBarAction(text = "Save")
            }
        )
        TaskyTopBar(
            title = text,
            toolbarActions = { color->
                TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                TaskyToolBarAction(text = "Save", tint = color)
            }
        )
        TaskyTopBar(
            title = text,
            toolbarActions = { color->
                TaskyToolBarAction(iconId = R.drawable.ic_visibility, tint = color)
                TaskyToolBarAction(iconId = R.drawable.ic_visibility)
            }
        )
    }
}