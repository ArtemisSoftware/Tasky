package com.artemissoftware.core.presentation.composables.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.theme.Black


@Composable
fun TaskyToolBarAction(
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
    tint: Color = Black,
    onClicked: () -> Unit = {}
) {

    IconButton(
        onClick = {
            onClicked()
        },
        content = {
            Icon(
                modifier = modifier,
                painter = painterResource(id = iconId),
                contentDescription = "icon",
                tint = tint
            )
        }
    )
}

@Composable
fun TaskyToolBarAction(
    text: String,
    modifier: Modifier = Modifier,
    tint: Color = Black,
    style: TextStyle = MaterialTheme.typography.subtitle1,
    onClicked: () -> Unit = {}
) {

    TaskyTextButton(
        modifier = modifier,
        allCaps = false,
        text = text,
        color = tint,
        style = style,
        onClick = onClicked
    )
}


@Preview(showBackground = true)
@Composable
private fun TaskyToolbarActionPreview() {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TaskyToolBarAction(
            iconId = R.drawable.ic_add,
            tint = Color.Black
        )

        TaskyToolBarAction(text = "Save")

    }
}
