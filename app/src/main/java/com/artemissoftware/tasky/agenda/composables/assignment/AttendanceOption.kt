package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.DarkGray
import com.artemissoftware.core.presentation.theme.Light2
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R

@Composable
fun AttendanceOption(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = Black,
    unselectedColor: Color = Light2,
    selectedTextColor: Color = White,
    unselectedTextColor: Color = DarkGray,
    isSelected: Boolean = false,
    onClick: (Boolean) -> Unit,
) {
    val colors by remember(key1 = isSelected) {
        mutableStateOf(
            getColors(
                isSelected = isSelected,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                selectedTextColor = selectedTextColor,
                unselectedTextColor = unselectedTextColor,
            ),
        )
    }

    Card(
        modifier = modifier
            .clickable {
                onClick.invoke(isSelected)
            },
        shape = RoundedCornerShape(100.dp),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .background(color = colors.first)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            TaskyText(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.overline,
                color = colors.second,
                text = stringResource(id = title),
            )
        }
    }
}

private fun getColors(
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    selectedTextColor: Color,
    unselectedTextColor: Color,
): Pair<Color, Color> {
    return if (isSelected) Pair(selectedColor, selectedTextColor) else Pair(unselectedColor, unselectedTextColor)
}

@Preview(showBackground = true)
@Composable
private fun VisitorItemPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AttendanceOption(
            title = R.string.app_name,
            modifier = Modifier
                .height(30.dp)
                .width(100.dp),
            onClick = {
            },
        )
        AttendanceOption(
            title = R.string.app_name,
            isSelected = true,
            modifier = Modifier
                .height(30.dp),
            onClick = {
            },
        )
    }
}
