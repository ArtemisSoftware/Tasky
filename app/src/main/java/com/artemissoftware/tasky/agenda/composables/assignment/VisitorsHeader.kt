package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.icon.TaskySquareIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Gray
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.TaskySquare
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun VisitorsHeader(
    modifier: Modifier = Modifier,
    isEditing: Boolean = false
) {

    Column(
        modifier = modifier
    ) {
        Row {

            TaskyText(
                style = MaterialTheme.typography.h6,
                text = stringResource(id = R.string.visitors)
            )

            Spacer(modifier = Modifier.width(12.dp))

            AnimatedVisibility(
                visible = isEditing,
                enter = VisibilityTransitions.enterEdition(),
                exit = VisibilityTransitions.exitEdition()
            ) {
                TaskySquareIcon(
                    padding = 10.dp,
                    size = 34.dp,
                    icon = R.drawable.ic_add,
                    iconColor = Gray
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AttendanceOption(
                title = R.string.all,
                modifier = Modifier
                    .height(30.dp)
                    .width(100.dp)
            )
            AttendanceOption(
                title = R.string.going,
                modifier = Modifier
                    .height(30.dp)
                    .width(100.dp)
            )
            AttendanceOption(
                title = R.string.not_going,
                modifier = Modifier
                    .height(30.dp)
                    .width(100.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun VisitorsHeaderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        VisitorsHeader(isEditing = true, modifier = Modifier.fillMaxWidth())
        VisitorsHeader(isEditing = false, modifier = Modifier.fillMaxWidth())
    }
}