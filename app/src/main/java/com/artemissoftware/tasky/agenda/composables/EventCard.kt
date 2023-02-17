package com.artemissoftware.tasky.agenda.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R

@Composable
fun EventCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {

            Row {

                Column{
                    EventRadioButton(
                        modifier = Modifier.padding(top = 4.dp),
                        onCheckedChange = {}
                    )
                }

                Column (
                    modifier = Modifier
                        .padding(start = 12.dp)
                ){
                    Row {
                        Text(
                            modifier = Modifier.weight(0.9F),
                            text = "Title - Text with LineThrough",
                            style = MaterialTheme.typography.h6.copy(textDecoration = TextDecoration.LineThrough)
                        )

                        Icon(
                            modifier = Modifier.weight(0.1F),
                            painter = painterResource( R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }

                    Text(
                        maxLines = 2,
                        text = "COmment - Text with LineThroughText with LineThroughText with LineThroughText with LineThrough",
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = "Time - Text with LineThroughText",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        EventCard()
        EventCard()
        EventCard()
        EventCard()
    }
}
