package com.artemissoftware.tasky.agenda.composables.assignment

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.tasky.R

@Composable
fun AttendanceOption( // TODO: mudar os text para TYtext e as fontes
    @StringRes title: Int,
    isSelected: Boolean = false
) {

    var colors by remember {
        mutableStateOf(getColors(isSelected = isSelected))
    }

    Card(
        modifier = Modifier
            .height(30.dp)
            .clickable {
                colors = getColors(!isSelected)
            },
        shape = RoundedCornerShape(100.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .background(color = colors.first)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                color = colors.second,
                text = stringResource(id = title)
            )
        }
    }

}

private fun getColors(isSelected: Boolean): Pair<Color, Color>{
    return if(isSelected) Pair(Color.Green, Color.Black) else Pair(Color.Black, Color.Green)
}

@Preview(showBackground = true)
@Composable
private fun VisitorItemPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        AttendanceOption(title = R.string.app_name)
        AttendanceOption(title = R.string.app_name, isSelected = true)
    }
}