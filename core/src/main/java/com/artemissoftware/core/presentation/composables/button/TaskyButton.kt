package com.artemissoftware.core.presentation.composables.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.White

@Composable
fun TaskyButton(
    text : String,
    onClick: () -> Unit,
    allCaps: Boolean = true,
    modifier: Modifier = Modifier,
    textColor: Color = White,
    backGroundColor: Color = Black
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(38.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backGroundColor
        ),
        onClick = { onClick.invoke() }
    ) {
        TaskyText(
            allCaps = allCaps,
            style = MaterialTheme.typography.button,
            text = text,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyButtonPreview() {

    TaskyButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        text = "GET STARTED",
        onClick = {}
    )
}

