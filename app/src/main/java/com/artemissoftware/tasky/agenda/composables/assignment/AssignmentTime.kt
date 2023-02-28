package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.TYIcon
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentTime( // TODO: mudar os text para TYtext e as fontes
    @StringRes title: Int,
    hour: String,
    day: String,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false
) {

    Column {
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {

            Column(
                modifier = Modifier.weight(0.5F)
            ){

                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = title),
                        modifier = Modifier.weight(0.5F)
                    )
                    Text(
                        text = hour,
                        modifier = Modifier.weight(0.4F)
                    )
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
            }

            Column(
                modifier = Modifier.weight(0.5F)
            ){

                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = day,
                        modifier = Modifier.weight(0.9F)
                    )
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
            }
        }

        TYDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignmentTimePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentTime(title = R.string.from, day = "Jul 21 2022", hour = "08:00")
        AssignmentTime(isEditing = true, title = R.string.to, day = "Jul 21 2022", hour = "08:30")
    }
}