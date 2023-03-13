package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.tasky.util.VisibilityTransitions

@Composable
fun AssignmentTime(
    @StringRes title: Int,
    hour: String,
    day: String,
    onTimeClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.weight(0.5F)
            ){

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable(enabled = isEditing, onClick = { onTimeClick() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TaskyText(
                        text = stringResource(id = title),
                        modifier = Modifier.weight(0.5F)
                    )
                    TaskyText(
                        text = hour,
                        modifier = Modifier.weight(0.3F)
                    )
                    Column(
                        modifier = Modifier.weight(0.2F),
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
            }

            Column(
                modifier = Modifier.weight(0.5F)
            ){

                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TaskyText(
                        textAlign = TextAlign.Center,
                        text = day,
                        modifier = Modifier.weight(0.8F)
                    )
                    Column(
                        modifier = Modifier.weight(0.2F),
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignmentTimePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AssignmentTime(title = R.string.from, day = "Jul 21 2022", hour = "08:00", modifier = Modifier.fillMaxWidth(), onTimeClick = {})
        AssignmentTime(isEditing = true, title = R.string.to, day = "Jul 21 2022", hour = "08:30", modifier = Modifier.fillMaxWidth(), onTimeClick = {})
    }
}