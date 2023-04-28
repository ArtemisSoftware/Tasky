package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentDescription(
    description: String,
    onEditClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body1,
    isEditing: Boolean = false,
) {
    Column(
        modifier = modifier
            .clickable {
                if (isEditing) onEditClick(description)
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(0.9F),
            ) {
                TaskyText(
                    style = style,
                    text = description,
                )
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

@Preview(showBackground = true)
@Composable
private fun AssignmentHeaderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentDescription(description = "First description", modifier = Modifier.fillMaxWidth(), onEditClick = {})
        AssignmentDescription(isEditing = true, description = "Second description", modifier = Modifier.fillMaxWidth(), onEditClick = {})
        AssignmentDescription(isEditing = true, description = "Second description of a really long one to prove that size is important for the space available", modifier = Modifier.fillMaxWidth(), onEditClick = {})
    }
}
