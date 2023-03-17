package com.artemissoftware.tasky.agenda.presentation.dashboard.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyAvatar
import com.artemissoftware.core.presentation.composables.TaskyDropDown
import com.artemissoftware.core.presentation.composables.dropdown.TaskyDropDownItem
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.LightBlue

@Composable
fun AgendaTopBar(
    modifier: Modifier = Modifier,
    toolbarActionsLeft: @Composable RowScope.() -> Unit = {},
    toolbarActions: @Composable RowScope.() -> Unit = {},
    backGroundColor: Color = Green,
) {
    val barModifier = Modifier
        .background(color = backGroundColor)
        .fillMaxWidth()
        .height(56.dp).then(modifier)

    Row(
        modifier = barModifier
            .background(color = backGroundColor)
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(modifier = Modifier.weight(5F), content = toolbarActionsLeft, horizontalArrangement = Arrangement.Start)
        Row(modifier = Modifier.weight(5F), content = toolbarActions, horizontalArrangement = Arrangement.End)
    }
}

@Preview(showBackground = true)
@Composable
private fun AgendaTopBarPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        AgendaTopBar(
            modifier = Modifier.padding(horizontal = 8.dp),
            toolbarActionsLeft = {
                TaskyDropDown<String>(
                    options = listOf("Tasky 1", "Tasky 2", "Tasky 3"),
                    defaultOption = "priority",
                    onOptionSelected = {},
                    addDivider = true,
                    modifier = Modifier.width(140.dp),
                    menuOption = {
                        TaskyDropDownItem(text = "Tasky")
                    },
                )
            },
            toolbarActions = { TaskyAvatar(text = "BW", circleColor = LightBlue) },
        )
    }
}
