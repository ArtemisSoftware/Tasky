package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.animation.AnimatedVisibility
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
import com.artemissoftware.tasky.agenda.composables.TYSquare
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun VisitorsHeader( // TODO: mudar os text para TYtext e as fontes e icons
    isEditing: Boolean = false
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {

            Text(text = stringResource(id = R.string.visitors))

            Spacer(modifier = Modifier.width(12.dp))

            AnimatedVisibility(
                visible = isEditing,
                enter = VisibilityTransitions.enterEdition(),
                exit = VisibilityTransitions.exitEdition()
            ) {
                TYSquare(
                    size = 20.dp,
                    color = Color.Red,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AttendanceOption(title = R.string.all)
            AttendanceOption(title = R.string.going)
            AttendanceOption(title = R.string.not_going)
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun VisitorsHeaderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        VisitorsHeader(isEditing = true)
        VisitorsHeader(isEditing = false)
    }
}