package com.artemissoftware.tasky.agenda.presentation.dashboard.composables.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.artemissoftware.core.presentation.composables.dropdown.TaskyDropDownItem
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.icon.TaskyIconToggleButton
import com.artemissoftware.core.presentation.composables.menu.TaskyPopupMenu
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.util.constants.DateTimePatternsConstants
import com.artemissoftware.core.util.extensions.format
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.AgendaItemType
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItemOption
import com.artemissoftware.tasky.agenda.presentation.dashboard.models.AgendaItemStyle

@Composable
fun TaskCard(
    task: AgendaItem.Task,
    onCheckedChange: (Boolean) -> Unit,
    onOptionClick: (AgendaItemOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = AgendaItemStyle.Task.color,
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
        ) {
            Row {
                TaskyIconToggleButton(
                    selected = task.isDone,
                    modifier = Modifier.padding(top = 4.dp),
                    onIcon = R.drawable.ic_round_check,
                    offIcon = R.drawable.ic_circle,
                    onIconColor = AgendaItemStyle.Task.generalTextColor,
                    onCheckedChange = onCheckedChange,
                )

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TaskyText(
                            color = AgendaItemStyle.Task.generalTextColor,
                            modifier = Modifier.weight(0.9F),
                            text = task.itemTitle,
                            style = MaterialTheme.typography.h6.copy(textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None),
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
                                    color = AgendaItemStyle.Task.generalTextColor,
                                )
                            },
                        )
                    }

                    TaskyText(
                        modifier = Modifier.padding(top = 12.dp),
                        color = AgendaItemStyle.Task.secondaryTextColor,
                        maxLines = 2,
                        text = task.description ?: "",
                        style = MaterialTheme.typography.body2,
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp)) {
                TaskyText(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    color = AgendaItemStyle.Task.secondaryTextColor,
                    text = task.starDate.format(DateTimePatternsConstants.DATE_TIME_PATTERN_MMM_d_HH_mm),
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        TaskCard(
            modifier = Modifier
                .fillMaxWidth(),
            onCheckedChange = {},
            onOptionClick = {},
            task = AgendaItem.mockTask,
        )
    }
}
