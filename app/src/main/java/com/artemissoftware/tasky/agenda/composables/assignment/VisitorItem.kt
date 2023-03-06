package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.artemissoftware.core.presentation.theme.*
import com.artemissoftware.core.util.StringUtil
import com.artemissoftware.tasky.R

@Composable
fun VisitorItem(
    name: String,
    modifier: Modifier = Modifier,
    isCreator: Boolean = false
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .background(color = Light2)
                .padding(vertical = 8.dp)
                .padding(start = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.weight(0.8F),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TaskyAvatar(
                    modifier = Modifier,
                    textColor = White,
                    circleColor = Gray,
                    size = 32.dp,
                    text = StringUtil.getInitials(name)
                )
                Spacer(modifier = Modifier.width(16.dp))
                TaskyText(
                    color = DarkGray,
                    style = MaterialTheme.typography.body2,
                    text = name
                )
            }

            Box(
                modifier = Modifier.weight(0.2F)
            ) {
                if (isCreator) {
                    TaskyText(
                        text = stringResource(id = R.string.creator),
                        modifier = Modifier.align(Alignment.CenterEnd),
                        style = MaterialTheme.typography.body2,
                        color = LightBlue
                    )
                } else {
                    TaskyIcon(
                        size = 20.dp,
                        icon = R.drawable.ic_trash,
                        color = DarkGray,
                        modifier = Modifier.align(Alignment.CenterEnd)
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
            isCreator = true,
            name = "Bruce Wayne",
            modifier = Modifier.fillMaxWidth().height(46.dp)
        )
        VisitorItem(
            name = "Dick Graysone",
            modifier = Modifier.fillMaxWidth().height(46.dp)
        )
    }
}