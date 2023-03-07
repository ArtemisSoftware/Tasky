package com.artemissoftware.tasky.agenda.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.DarkGray
import com.artemissoftware.core.presentation.theme.Gray
import com.artemissoftware.core.presentation.theme.Orange

@Composable
fun WeekDay(
    weekDay: String,
    dayOfTheWeek: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(if(isSelected) Orange else Color.Transparent)
            .clickable {
                onClick.invoke()
            }
    ){
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskyText(
                color = if(isSelected) DarkGray else Gray,
                text = weekDay,
                style = MaterialTheme.typography.overline
            )
            TaskyText(
                color = DarkGray,
                text = dayOfTheWeek,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekDayPreview() {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        WeekDay(
            modifier = Modifier
            .size(width = 40.dp, height = 60.dp),
            weekDay = "M",
            dayOfTheWeek = "1"
        )
        WeekDay(
            modifier = Modifier
                .size(width = 40.dp, height = 60.dp),
            weekDay = "T",
            dayOfTheWeek = "2",
            isSelected = true
        )
        WeekDay(
            modifier = Modifier
                .size(width = 40.dp, height = 60.dp),
            weekDay = "W",
            dayOfTheWeek = "13"
        )
        WeekDay(
            modifier = Modifier
                .size(width = 40.dp, height = 60.dp),
            weekDay = "F",
            dayOfTheWeek = "23",
            isSelected = true
        )
    }
}