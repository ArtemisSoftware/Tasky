package com.artemissoftware.core.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.dropdown.TaskyDropDownItem
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.presentation.theme.LightBlue

@Composable
fun <T>TaskyPopupMenu(
    options: List<T>,
    placeHolder: @Composable () -> Unit,
    onOptionSelected: (T) -> Unit,
    menuOption: @Composable (T) -> Unit,
    addDivider: Boolean = false,
) {
    var expanded by remember { mutableStateOf(true) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        Modifier
            .onGloballyPositioned {
                parentSize = it.size
            }
            .wrapContentSize(Alignment.TopEnd)
            .clickable { expanded = true },
    ) {
        placeHolder()
    }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .padding(top = with(LocalDensity.current) { parentSize.height.toDp() }),
    ) {
        DropdownMenu(
            modifier = Modifier.wrapContentWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEachIndexed { index, item ->

                val isLast = (index == options.size - 1)

                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onOptionSelected.invoke(item)
                    },
                ) {
                    menuOption(item)
                }

                if (addDivider && !isLast) {
                    Divider(color = Light)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyPopupMenuPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TaskyPopupMenu(
            options = listOf("Tasky 1", "Tasky 2", "Tasky 3"),
            placeHolder = { TaskyAvatar(text = "BW", circleColor = LightBlue) },
            onOptionSelected = {},
            menuOption = {
                TaskyDropDownItem(text = "Tasky")
            },
        )

        TaskyPopupMenu(
            options = listOf("Tasky 1", "Tasky 2", "Tasky 3"),
            onOptionSelected = {},
            menuOption = {
                TaskyDropDownItem(text = "Tasky")
            },
            placeHolder = {
                TaskyIcon(
                    modifier = Modifier
                        .weight(0.1F),
                    icon = R.drawable.ic_add,
                    color = Green,
                )
            },
        )
    }
}
