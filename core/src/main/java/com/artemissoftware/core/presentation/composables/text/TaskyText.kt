package com.artemissoftware.core.presentation.composables.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.ErrorRed
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.InterTypography
import java.util.*

@Composable
fun TaskyText(
    text: String,
    modifier: Modifier = Modifier,
    allCaps: Boolean = false,
    color: Color = Black,
    textAlign: TextAlign? = null,
    style: TextStyle = MaterialTheme.typography.body2,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = if (allCaps) text.uppercase(Locale.ROOT) else text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        style = style,
        letterSpacing = letterSpacing,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun TaskyText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    allCaps: Boolean = false,
    color: Color = Black,
    textAlign: TextAlign? = null,
    style: TextStyle = MaterialTheme.typography.body2,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = if (allCaps) text.toUpperCase() else text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        style = style,
        letterSpacing = letterSpacing,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Preview
@Composable
private fun TaskyTextPreview() {
    MaterialTheme(typography = InterTypography) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TaskyText(
                text = "MaterialTheme.typography.h4",
                style = MaterialTheme.typography.h4,
            )

            TaskyText(
                text = "MaterialTheme.typography.body1",
                style = MaterialTheme.typography.body1,
            )

            TaskyText(
                text = "MaterialTheme.typography.caption",
                style = MaterialTheme.typography.caption,
            )

            TaskyText(
                text = "MaterialTheme.typography.overline",
                style = MaterialTheme.typography.overline,
            )

            TaskyText(
                text = "MaterialTheme.typography.button",
                style = MaterialTheme.typography.button,
            )

            TaskyText(
                allCaps = true,
                text = "MaterialTheme.typography.button - all caps",
                style = MaterialTheme.typography.button,
            )

            TaskyText(
                text = buildAnnotatedString {
                    append("MaterialTheme.typography.button 1 ")
                    withStyle(
                        style = SpanStyle(
                            color = Green,
                        ),
                    ) {
                        append("different colors")
                    }
                },
                allCaps = true,
                color = ErrorRed,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}
