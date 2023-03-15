package com.artemissoftware.core.presentation.composables.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.presentation.theme.White

@Composable
fun TaskyDropDownItem(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    allCaps: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.subtitle2
) {

    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(color = backgroundColor)
    ) {
        TaskyText(
            allCaps = allCaps,
            modifier = Modifier,
            text = text,
            style = textStyle
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(9.dp)
                .padding(top = 8.dp),
            color = Light
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyDropDownPreview() {
    TaskyDropDownItem(text = "Tasky", backgroundColor = Green)
}