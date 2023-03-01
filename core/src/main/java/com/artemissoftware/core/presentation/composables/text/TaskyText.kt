package com.artemissoftware.core.presentation.composables.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.InterTypography

@Composable
fun TaskyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Black,
    textAlign: TextAlign? = null,
    style: TextStyle = MaterialTheme.typography.body2,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {

    Text(
        text = text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        style = style,
        letterSpacing = letterSpacing,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Preview
@Composable
private fun TaskyTextPreview() {

    MaterialTheme(typography = InterTypography){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TaskyText(
                text = "MaterialTheme.typography.h4",
                style = MaterialTheme.typography.h4
            )

            TaskyText(
                text = "MaterialTheme.typography.body1",
                style = MaterialTheme.typography.body1
            )

            TaskyText(
                text = "MaterialTheme.typography.caption",
                style = MaterialTheme.typography.caption
            )

            TaskyText(
                text = "MaterialTheme.typography.overline",
                style = MaterialTheme.typography.overline
            )

            TaskyText(
                text = "MaterialTheme.typography.button",
                style = MaterialTheme.typography.button
            )
        }
    }

}