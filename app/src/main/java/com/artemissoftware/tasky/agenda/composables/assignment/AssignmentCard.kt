package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.menu.TaskyPopupMenu
import com.artemissoftware.core.presentation.composables.dropdown.TaskyDropDownItem
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.icon.TaskyIconToggleButton
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItemOption

@Composable
fun AssignmentCard(
    agendaItemType: AgendaItemType,
    title: String,
    date: String,
    onCheckedChange: (Boolean) -> Unit,
    onOptionClick: (AgendaItemOption) -> Unit,
    modifier: Modifier = Modifier,
    options: Array<AgendaItemOption> = AgendaItemOption.values(),
    description: String? = null,
    isComplete: Boolean = false,
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = agendaItemType.color,
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
        ) {
            Row {
                Column {
                    TaskyIconToggleButton(
                        modifier = Modifier.padding(top = 4.dp),
                        onIcon = R.drawable.ic_round_check,
                        offIcon = R.drawable.ic_circle,
                        onIconColor = agendaItemType.generalTextColor,
                        onCheckedChange = onCheckedChange,
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TaskyText(
                            color = agendaItemType.generalTextColor,
                            modifier = Modifier.weight(0.9F),
                            text = title,
                            style = MaterialTheme.typography.h6.copy(textDecoration = if (isComplete) TextDecoration.LineThrough else TextDecoration.None),
                        )

                        TaskyPopupMenu(
                            options = AgendaItemOption.values().toList(),
                            onOptionSelected = {
                                onOptionClick(it)
                            },
                            menuOption = {
                                TaskyDropDownItem(text = stringResource(id = it.descriptionId))
                            },
                            placeHolder = {
                                TaskyIcon(
                                    modifier = Modifier
                                        .weight(0.1F),
                                    icon = R.drawable.ic_more_options,
                                    color = agendaItemType.generalTextColor,
                                )
                            },
                        )
                    }

                    TaskyText(
                        modifier = Modifier.padding(top = 12.dp),
                        color = agendaItemType.secondaryTextColor,
                        maxLines = 2,
                        text = description ?: "",
                        style = MaterialTheme.typography.caption,
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp)) {
                TaskyText(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    color = agendaItemType.secondaryTextColor,
                    text = date,
                    style = MaterialTheme.typography.body2,
                )
            }
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
            agendaItemType = AgendaItemType.Task(),
            onCheckedChange = {},
            onOptionClick = {},
            title = "title",
            description = "description",
            date = "date",
        )
        AssignmentCard(
            modifier = Modifier
                .fillMaxWidth(),
            agendaItemType = AgendaItemType.Event(),
            onCheckedChange = {},
            onOptionClick = {},
            title = "title",
            description = "description",
            date = "date",
        )
        AssignmentCard(
            modifier = Modifier
                .fillMaxWidth(),
            agendaItemType = AgendaItemType.Reminder(),
            onCheckedChange = {},
            onOptionClick = {},
            title = "title",
            description = "description",
            date = "date",
        )
    }
}
