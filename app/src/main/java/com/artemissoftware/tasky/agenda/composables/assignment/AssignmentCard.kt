package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.presentation.dashboard.composables.cards.ReminderCard
import com.artemissoftware.tasky.agenda.presentation.dashboard.composables.cards.TaskCard
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItemOption

@Composable
fun AssignmentCard(
    agendaItem: AgendaItem,
    onCheckedChange: (Boolean) -> Unit,
    onOptionClick: (AgendaItemOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (agendaItem) {
        is AgendaItem.Reminder -> {
            ReminderCard(
                modifier = modifier,
                onOptionClick = onOptionClick,
                reminder = agendaItem,
            )
        }
        is AgendaItem.Task -> {
            TaskCard(
                modifier = modifier,
                onCheckedChange = onCheckedChange,
                onOptionClick = onOptionClick,
                task = agendaItem,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignmentCardPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        AssignmentCard(
            modifier = Modifier
                .fillMaxWidth(),
            onCheckedChange = {},
            onOptionClick = {},
            agendaItem = AgendaItem.mockReminder,
        )
        AssignmentCard(
            modifier = Modifier
                .fillMaxWidth(),
            onCheckedChange = {},
            onOptionClick = {},
            agendaItem = AgendaItem.mockTask,
        )
        AssignmentCard(
            modifier = Modifier
                .fillMaxWidth(),
            onCheckedChange = {},
            onOptionClick = {},
            agendaItem = AgendaItem.mockReminder,
        )
    }
}
