package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.TYIcon
import com.artemissoftware.tasky.agenda.composables.TYSquare
import com.artemissoftware.tasky.agenda.composables.WeekDay

@Composable
fun AssignmentHeader() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            TYSquare(
                size = 20.dp,
                color = Color.Green
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Event")
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
                TYIcon(icon = R.drawable.ic_launcher_foreground)
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
    AssignmentHeader()

}