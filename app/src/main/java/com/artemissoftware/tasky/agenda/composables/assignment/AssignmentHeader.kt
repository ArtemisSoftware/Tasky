package com.artemissoftware.tasky.agenda.composables.assignment

import android.view.animation.BounceInterpolator
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.Assignment
import com.artemissoftware.tasky.agenda.composables.TYIcon
import com.artemissoftware.tasky.agenda.composables.TYSquare
import com.artemissoftware.tasky.agenda.composables.WeekDay
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentHeader(
    assignment: Assignment,
    isEditing: Boolean = false
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            TYSquare(
                size = 20.dp,
                color = assignment.color,
                borderColor = assignment.borderColor
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = stringResource(id = assignment.title))
        }

        Row(
            modifier = Modifier.padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(0.9F)
            ) {
                Row {
                    TYIcon(icon = R.drawable.ic_launcher_foreground)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Meeting")
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
                    TYIcon(icon = R.drawable.ic_launcher_foreground)
                }
            }

        }

        TYDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignmentHeaderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentHeader(assignment = Assignment.Reminder())
        AssignmentHeader(assignment = Assignment.Reminder(), isEditing = true)
    }

}