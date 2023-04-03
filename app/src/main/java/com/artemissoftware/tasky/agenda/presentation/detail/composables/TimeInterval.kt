package com.artemissoftware.tasky.agenda.presentation.detail.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.util.DateTimePatternsConstants
import com.artemissoftware.core.util.extensions.format
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.assignment.AssignmentTime
import java.time.LocalDateTime

@Composable
fun TimeInterval(
    isEditing: Boolean,
    startDate: LocalDateTime,
    onStartTimeClick: () -> Unit,
    onStartDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssignmentTime(
        isEditing = isEditing,
        title = R.string.at,
        day = startDate.format(pattern = DateTimePatternsConstants.DATE_PATTERN_MMM_dd_yyyy),
        hour = startDate.toLocalTime().format(pattern = DateTimePatternsConstants.TIME_PATTERN_HH_mm),
        modifier = modifier.fillMaxWidth(),
        onTimeClick = {
            onStartTimeClick()
        },
        onDateClick = {
            onStartDateClick()
        },
    )
}

@Composable
fun TimeInterval(
    isEditing: Boolean,
    startDate: LocalDateTime,
    onStartTimeClick: () -> Unit,
    onStartDateClick: () -> Unit,
    endDate: LocalDateTime,
    onEndTimeClick: () -> Unit,
    onEndDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssignmentTime(
        isEditing = isEditing,
        title = R.string.from,
        day = startDate.format(pattern = DateTimePatternsConstants.DATE_PATTERN_MMM_dd_yyyy),
        hour = startDate.toLocalTime().format(pattern = DateTimePatternsConstants.TIME_PATTERN_HH_mm),
        modifier = modifier.fillMaxWidth(),
        onTimeClick = {
            onStartTimeClick()
        },
        onDateClick = {
            onStartDateClick()
        },
    )

    DetailDivider(top = 28.dp, bottom = 28.dp, modifier = Modifier.fillMaxWidth())

    AssignmentTime(
        isEditing = isEditing,
        title = R.string.to,
        day = endDate.format(pattern = DateTimePatternsConstants.DATE_PATTERN_MMM_dd_yyyy),
        hour = endDate.toLocalTime().format(pattern = DateTimePatternsConstants.TIME_PATTERN_HH_mm),
        modifier = modifier.fillMaxWidth(),
        onTimeClick = {
            onEndTimeClick()
        },
        onDateClick = {
            onEndDateClick()
        },
    )
}
