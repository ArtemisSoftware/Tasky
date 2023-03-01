package com.artemissoftware.core_ui.composables.button

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
import com.artemissoftware.core_ui.composables.text.TaskyText
import com.artemissoftware.core_ui.theme.Black
import com.artemissoftware.core_ui.theme.White

@Composable
fun TaskyButton(
    modifier: Modifier = Modifier,
    text : String,
    onClick: () -> Unit,
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

