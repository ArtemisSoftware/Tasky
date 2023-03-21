package com.artemissoftware.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.dropdown.TaskyDropDownItem
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.theme.*

@Composable
fun <T>TaskyDropDown(
    options: List<T>,
    placeHolder: @Composable RowScope.() -> Unit,
    menuOption: @Composable (T) -> Unit,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    addDivider: Boolean = false,
    height: Dp = 36.dp,
    backgroundColor: Color = White,
) {
    var expanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = modifier
            .onGloballyPositioned {
                parentSize = it.size
            }
            .background(backgroundColor)
            .height(height)
            .clickable { expanded = true },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        placeHolder()
    }
    DropdownMenu(
        modifier = Modifier
            .width(with(LocalDensity.current) { parentSize.width.toDp() }),
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        options.forEachIndexed { index, item ->

            val isLast = (index == options.size - 1)

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onOptionSelected(item)
                },
            ) {
                menuOption(item)
            }

            if (addDivider && !isLast) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(9.dp)
                        .padding(top = 8.dp),
                    color = Light,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyDropDownPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TaskyDropDown<String>(
            options = listOf("Tasky 1", "Tasky 2", "Tasky 3"),
            onOptionSelected = {},
            placeHolder = {
                TaskyText(
                    modifier = Modifier
                        .weight(weight = 8.5f),
                    text = "defaultOption",
                )

                TaskyIcon(
                    size = 24.dp,
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(weight = 1.5f),
                    icon = Icons.Filled.ArrowDropDown,
                    color = Black,
                )
            },
            addDivider = true,
            modifier = Modifier.width(140.dp),
            menuOption = {
                TaskyDropDownItem(text = "Tasky")
            },
        )

        TaskyDropDown<String>(
            options = listOf("Tasky 1", "Tasky 2"),
            placeHolder = {
                TaskyText(
                    modifier = Modifier
                        .weight(weight = 8.5f),
                    text = "defaultOption",
                )

                TaskyIcon(
                    size = 24.dp,
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(weight = 1.5f),
                    icon = Icons.Filled.ArrowDropDown,
                    color = Black,
                )
            },
            backgroundColor = Black,
            onOptionSelected = {},
            modifier = Modifier.fillMaxWidth(),
            menuOption = {
                Text(
                    modifier = Modifier,
                    text = it,
                    style = MaterialTheme.typography.subtitle2,
                )
            },
        )
    }
}
