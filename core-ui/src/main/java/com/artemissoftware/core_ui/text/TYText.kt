package com.artemissoftware.core_ui.text

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
import com.artemissoftware.core_ui.theme.Black
import com.artemissoftware.core_ui.theme.InterTypography

@Composable
fun TYText(
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
private fun TYTextPreview() {

    MaterialTheme(typography = InterTypography){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TYText(
                text = "MaterialTheme.typography.h4",
                style = MaterialTheme.typography.h4
            )

            TYText(
                text = "MaterialTheme.typography.body1",
                style = MaterialTheme.typography.body1
            )

            TYText(
                text = "MaterialTheme.typography.caption",
                style = MaterialTheme.typography.caption
            )

            TYText(
                text = "MaterialTheme.typography.overline",
                style = MaterialTheme.typography.overline
            )

            TYText(
                text = "MaterialTheme.typography.button",
                style = MaterialTheme.typography.button
            )
        }
    }

}