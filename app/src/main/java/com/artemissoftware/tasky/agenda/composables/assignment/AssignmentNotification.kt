package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.icon.TaskySquareIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Gray
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentNotification(
    description: String,
    isEditing: Boolean = false,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(0.9F)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TaskySquareIcon(
                        size = 30.dp,
                        icon = R.drawable.ic_notification,
                        iconColor = Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    TaskyText(
                        style = MaterialTheme.typography.body1,
                        text = description
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

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            color = Light
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun AssignmentTimePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentNotification(description = "First description", modifier = Modifier.fillMaxWidth())
        AssignmentNotification(isEditing = true, description = "Second descriptionsdçljgjlsdglkdshfgklsdhfgjklhsdflkjghsdlkjfghlkdsfhgklsdfhgjklsdhfgjklshdfkljghsdlkfgjdfhgjklsdfhgjsdfg", modifier = Modifier.fillMaxWidth())
    }

}