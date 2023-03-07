package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun VisitorList(
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
) {

    LazyColumn(modifier = modifier.fillMaxWidth()) {

//        item {
//            VisitorsHeader(isEditing = isEditing)
//        }
//        items(
//            items = myItems,
//            key = {item: MyItem ->
//                // Some unique id here
//                item.hashCode()
//            }
//        ) {
//
//        }
        item {
            Text(text = "ffoffofoffo")
        }
        items(1) { index ->
            VisitorItem(
                name = "Bruce wayne",
                modifier = if(index == 4) Modifier else Modifier.padding(bottom = 4.dp)
            )
        }
        item {
            Text(text = "ffoffofoffo")
        }
        items(1) { index ->
            VisitorItem(name = "Dick wayne")
        }

    }


}

@Preview(showBackground = true)
@Composable
private fun VisitorListPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        VisitorList(isEditing = true)
        VisitorList(isEditing = false)
    }
}