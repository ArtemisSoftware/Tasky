package com.artemissoftware.core.presentation.composables.snackbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.InterTypography
import com.artemissoftware.core.util.UiText

@Composable
fun TaskySnackBar(
    taskySnackBarType: TaskySnackBarType,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.White),
        backgroundColor = taskySnackBarType.color,
        modifier = modifier
            .padding(8.dp)
            .wrapContentSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TaskyIcon(icon = taskySnackBarType.imageVector)
            TaskyText(
                modifier = Modifier.padding(start = 8.dp),
                text = taskySnackBarType.text.asString(),
                style = MaterialTheme.typography.overline,
            )
        }
    }
}

@Preview
@Composable
private fun TaskySnackBarPreview() {
    MaterialTheme(typography = InterTypography) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TaskySnackBar(
                TaskySnackBarType.Info(
                    text = UiText.DynamicString("Tasky snack bar example"),
                    imageVector = Icons.Default.Notifications,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
