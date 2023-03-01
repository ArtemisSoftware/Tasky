package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.composables.TYIcon

@Composable
fun VisitorItem( // TODO: Mudar texto e icons
    isCreator: Boolean = false,
    name: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .clickable { },
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.Red)
                .padding(vertical = 8.dp)
                .padding(start = 8.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.weight(0.8F),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TYIcon(
                    modifier = Modifier,
                    icon = R.drawable.ic_launcher_foreground,
                    color = Color.Green
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = name)
            }

            Box(
                modifier = Modifier.weight(0.2F)
            ) {
                if (isCreator) {
                    Text(
                        text = stringResource(id = R.string.creator),
                        modifier = Modifier.align(Alignment.CenterEnd),
                        color = Color.Blue
                    )
                } else {
                    TYIcon(
                        icon = R.drawable.ic_launcher_foreground,
                        color = Color.Green,
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
        VisitorItem(isCreator = true, name = "Bruce Wayne")
        VisitorItem(name = "Dick Graysone")
    }
}