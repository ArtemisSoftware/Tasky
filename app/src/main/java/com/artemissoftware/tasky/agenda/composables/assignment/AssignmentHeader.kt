package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.geometric.TaskySquare
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.icon.TaskyIconToggleButton
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.DarkGray
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.AgendaItemType
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItemStyle
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentHeader(
    agendaItemStyle: AgendaItemStyle,
    title: String,
    onEditClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isDone: Boolean = false,
    style: TextStyle = MaterialTheme.typography.h4,
    onIsDoneClick: (Boolean) -> Unit = {},
    isEditing: Boolean = false,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TaskySquare(
                size = 20.dp,
                color = agendaItemStyle.color,
                borderColor = agendaItemStyle.borderColor,
            )
            Spacer(modifier = Modifier.width(12.dp))
            TaskyText(
                text = stringResource(id = agendaItemStyle.title),
                style = MaterialTheme.typography.subtitle1,
                color = DarkGray,
            )
        }

        Row(
            modifier = Modifier.padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(0.9F),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            if (isEditing) onEditClick(title)
                        },
                ) {
                    TaskyIconToggleButton(
                        selected = isDone,
                        size = 20.dp,
                        onIcon = R.drawable.ic_round_check,
                        offIcon = R.drawable.ic_circle,
                        onIconColor = agendaItemStyle.bulletColor,
                        onCheckedChange = onIsDoneClick,
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    TaskyText(
                        modifier = Modifier
                            .clickable {
                                if (isEditing) onEditClick(title)
                            },
                        text = title,
                        style = style,
                    )
                }
            }

            Column(
                modifier = Modifier.weight(0.1F),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AnimatedVisibility(
                    visible = isEditing,
                    enter = VisibilityTransitions.enterEdition(),
                    exit = VisibilityTransitions.exitEdition(),
                ) {
                    TaskyIcon(
                        icon = R.drawable.ic_right_arrow,
                        size = 24.dp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun AssignmentHeaderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentHeader(agendaItemStyle = AgendaItemStyle.Reminder, title = "First title", modifier = Modifier.fillMaxWidth(), onEditClick = {})
        AssignmentHeader(agendaItemStyle = AgendaItemStyle.Task, isEditing = true, title = "Second title", modifier = Modifier.fillMaxWidth(), onEditClick = {})
        AssignmentHeader(agendaItemStyle = AgendaItemStyle.Event, isEditing = true, title = "Third title", modifier = Modifier.fillMaxWidth(), onEditClick = {})
    }
}
