package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyAvatar
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.DarkGray
import com.artemissoftware.core.presentation.theme.Gray
import com.artemissoftware.core.presentation.theme.Light2
import com.artemissoftware.core.presentation.theme.LightBlue
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models.VisitorEvent

@Composable
fun VisitorItem(
    visitor: VisitorEvent.Visitor,
    onDeleteVisitor: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .background(color = Light2)
                .padding(vertical = 8.dp)
                .padding(start = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(0.8F),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TaskyAvatar(
                    modifier = Modifier,
                    textColor = White,
                    circleColor = Gray,
                    size = 32.dp,
                    text = visitor.attendee.fullName,
                )
                Spacer(modifier = Modifier.width(16.dp))
                TaskyText(
                    color = DarkGray,
                    style = MaterialTheme.typography.body2,
                    text = visitor.attendee.fullName,
                )
            }

            Box(
                modifier = Modifier.weight(0.2F),
            ) {
                if (visitor.isEventCreator) {
                    TaskyText(
                        text = stringResource(id = R.string.creator),
                        modifier = Modifier.align(Alignment.CenterEnd),
                        style = MaterialTheme.typography.body2,
                        color = LightBlue,
                    )
                } else {
                    TaskyIcon(
                        size = 20.dp,
                        icon = R.drawable.ic_trash,
                        color = DarkGray,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                onDeleteVisitor(visitor.attendee.id)
                            },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VisitorItemPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        VisitorItem(
            visitor = VisitorEvent.Visitor(
                attendee = Attendee(
                    fullName = "Bruce Wayne",
                    id = "222",
                    email = "Bruce@email.com",
                    isGoing = false,
                ),
                isEventCreator = true,
            ),
            onDeleteVisitor = {},
            modifier = Modifier.fillMaxWidth().height(46.dp),
        )
        VisitorItem(
            visitor = VisitorEvent.Visitor(
                attendee = Attendee(
                    fullName = "Dick Grayson",
                    id = "222",
                    email = "Bruce@email.com",
                    isGoing = false,
                ),
                isEventCreator = true,
            ),
            onDeleteVisitor = {},
            modifier = Modifier.fillMaxWidth().height(46.dp),
        )
    }
}
