package com.artemissoftware.core_ui.composables.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.composables.button.TYTextButton
import com.artemissoftware.core_ui.composables.text.TYText
import com.artemissoftware.core_ui.theme.Black


@Composable
fun TYToolBarAction(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
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
fun TYToolBarAction(
    modifier: Modifier = Modifier,
    text: String,
    tint: Color = Black,
    style: TextStyle = MaterialTheme.typography.subtitle1,
    onClicked: () -> Unit = {}
) {

    TYTextButton(
        modifier = modifier,
        text = text,
        color = tint,
        style = style,
        onClick = onClicked
    )
}


@Preview(showBackground = true)
@Composable
private fun FFToolbarActionPreview() {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TYToolBarAction(
            iconId = R.drawable.ic_add,
            tint = Color.Black
        )

        TYToolBarAction(text = "Save")

    }
}
