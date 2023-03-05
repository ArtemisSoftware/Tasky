package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.DarkGray
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.core.presentation.composables.geometric.TaskySquare
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentHeader(
    agendaItemType: AgendaItemType,
    title: String,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false
) {

    Column(
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            TaskySquare(
                size = 20.dp,
                color = agendaItemType.color,
                borderColor = agendaItemType.borderColor
            )
            Spacer(modifier = Modifier.width(12.dp))
            TaskyText(
                text = stringResource(id = agendaItemType.title),
                style = MaterialTheme.typography.subtitle1,
                color = DarkGray
            )
        }

        Row(
            modifier = Modifier.padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(0.9F),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TaskyIcon(
                        icon = R.drawable.ic_circle,
                        size = 20.dp
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    TaskyText(
                        text = title,
                        style = MaterialTheme.typography.h4
                    )
                }
            }

            Column(
                modifier = Modifier.weight(0.1F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(
                    visible = isEditing,
                    enter = VisibilityTransitions.enterEdition(),
                    exit = VisibilityTransitions.exitEdition()
                ) {
                    TaskyIcon(
                        icon = R.drawable.ic_right_arrow,
                        size = 30.dp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignmentHeaderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentHeader(agendaItemType = AgendaItemType.Reminder(), title = "First title", modifier = Modifier.fillMaxWidth())
        AssignmentHeader(agendaItemType = AgendaItemType.Task(), isEditing = true, title = "Second title", modifier = Modifier.fillMaxWidth())
        AssignmentHeader(agendaItemType = AgendaItemType.Event(), isEditing = true, title = "Third title", modifier = Modifier.fillMaxWidth())
    }

}