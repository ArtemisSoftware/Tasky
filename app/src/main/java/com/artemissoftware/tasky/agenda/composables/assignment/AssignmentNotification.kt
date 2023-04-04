package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.domain.models.agenda.NotificationType
import com.artemissoftware.core.presentation.composables.dropdown.TaskyDropDownItem
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.icon.TaskySquareIcon
import com.artemissoftware.core.presentation.composables.menu.TaskyPopupMenu
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Gray
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentNotification(
    selectedNotification: NotificationType,
    onNotificationSelected: (NotificationType) -> Unit,
    notificationOptions: List<NotificationType>,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
) {
    if (isEditing) {
        TaskyPopupMenu(
            options = notificationOptions,
            onOptionSelected = onNotificationSelected,
            menuOption = {
                TaskyDropDownItem(text = it.description.asString())
            },
            placeHolder = {
                AssignmentNotification(
                    isEditing = true,
                    description = selectedNotification.description.asString(),
                    modifier = modifier,
                )
            },
        )
    } else {
        AssignmentNotification(
            isEditing = false,
            description = selectedNotification.description.asString(),
            modifier = modifier,
        )
    }
}

@Composable
fun AssignmentNotification(
    description: String,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(0.9F),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TaskySquareIcon(
                        size = 30.dp,
                        icon = R.drawable.ic_notification,
                        iconColor = Gray,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    TaskyText(
                        style = MaterialTheme.typography.body1,
                        text = description,
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
                        size = 30.dp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun AssignmentTimePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentNotification(
            description = "First description",
            modifier = Modifier.fillMaxWidth(),
        )
        AssignmentNotification(
            isEditing = true,
            description = "Second description",
            modifier = Modifier.fillMaxWidth(),
        )
        AssignmentNotification(
            isEditing = true,
            modifier = Modifier.fillMaxWidth(),
            selectedNotification = NotificationType.THIRTY_MINUTES_BEFORE,
            onNotificationSelected = {},
            notificationOptions = NotificationType.values().toList(),
        )
    }
}
