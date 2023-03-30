package com.artemissoftware.tasky.agenda.presentation.detail.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentTime

@Composable
fun TimeInterval(
    isEditing: Boolean,
    startTime: String,
    onStartTimeClick: () -> Unit,
    startDate: String,
    onStartDateTimeClick: () -> Unit,
) {
    AssignmentTime(
        isEditing = isEditing,
        title = R.string.at,
        day = startDate,
        hour = startTime,
        modifier = Modifier.fillMaxWidth(),
        onTimeClick = {
            onStartTimeClick()
        },
        onDateClick = {
            onStartDateTimeClick()
        },
    )
}

@Composable
fun TimeInterval(
    isEditing: Boolean,
    startTime: String,
    onStartTimeClick: () -> Unit,
    startDate: String,
    onStartDateTimeClick: () -> Unit,
    endTime: String,
    onEndTimeClick: () -> Unit,
    endDate: String,
    onEndDateTimeClick: () -> Unit,
) {
    AssignmentTime(
        isEditing = isEditing,
        title = R.string.from,
        day = startDate,
        hour = startTime,
        modifier = Modifier.fillMaxWidth(),
        onTimeClick = {
            onStartTimeClick()
        },
        onDateClick = {
            onStartDateTimeClick()
        },
    )

    DetailDivider(top = 28.dp, bottom = 28.dp)

    AssignmentTime(
        isEditing = isEditing,
        title = R.string.to,
        day = endDate,
        hour = endTime,
        modifier = Modifier.fillMaxWidth(),
        onTimeClick = {
            onEndTimeClick()
        },
        onDateClick = {
            onEndDateTimeClick()
        },
    )
}
