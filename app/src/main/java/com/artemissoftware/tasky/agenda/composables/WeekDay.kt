package com.artemissoftware.tasky.agenda.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeekDay(
    weekDay: String,
    dayOfTheWeek: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .size(width = 22.dp, height = 37.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if(isSelected) Color.Red else Color.Transparent)
            .clickable {
                onClick.invoke()
            }
    ){
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weekDay,
                style = MaterialTheme.typography.overline
            )
            Text(
                text = dayOfTheWeek,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekDayPreview() {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        WeekDay(weekDay = "M", dayOfTheWeek = "1")
        WeekDay(weekDay = "T", dayOfTheWeek = "2", isSelected = true)
        WeekDay(weekDay = "W", dayOfTheWeek = "13")
        WeekDay(weekDay = "F", dayOfTheWeek = "23", isSelected = true)
    }
}