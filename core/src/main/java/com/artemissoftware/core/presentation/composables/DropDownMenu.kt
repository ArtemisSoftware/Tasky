package com.artemissoftware.core.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
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
    defaultOption: String,
    menuOption: @Composable (T) -> Unit,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    allCaps: Boolean = false,
    addDivider: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.subtitle2,
    height: Dp = 36.dp,
    backgroundColor: Color = White,
    textColor: Color = LightBlue
) {

    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = modifier
            .onGloballyPositioned {
                parentSize = it.size
            }
            .background(backgroundColor)
            .height(height)
            .clickable { expanded = true },
        verticalAlignment = Alignment.CenterVertically
    ) {

        TaskyText(
            modifier = Modifier
                .weight(weight = 8.5f),
            text = defaultOption,
            allCaps = allCaps,
            color = textColor,
            style = textStyle
        )

        TaskyIcon(
            size = 24.dp,
            modifier = Modifier
                .graphicsLayer {
                    rotationX = angle
                }
                .alpha(ContentAlpha.medium)
                .weight(weight = 1.5f),
            icon = Icons.Filled.ArrowDropDown,
            color = textColor
        )

        DropdownMenu(
            modifier = Modifier
                .width(with(LocalDensity.current) { parentSize.width.toDp() }),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEachIndexed { index, item ->

                val isLast = (index == options.size -1)

                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onOptionSelected(item)
                    }
                ) {
                    menuOption(item)
                }

                if(addDivider && !isLast) {

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(9.dp)
                            .padding(top = 8.dp),
                        color = Light
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TaskyDropDownPreview() {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        TaskyDropDown<String>(
            options = listOf("Tasky 1", "Tasky 2", "Tasky 3"),
            defaultOption = "priority",
            onOptionSelected = {},
            addDivider = true,
            modifier = Modifier.width(140.dp),
            menuOption = {

                TaskyDropDownItem(text = "Tasky")

            }
        )

        TaskyDropDown<String>(
            options = listOf("Tasky 1", "Tasky 2"),
            defaultOption = "priority2",
            textColor = Green,
            backgroundColor = Black,
            onOptionSelected = {},
            modifier = Modifier.fillMaxWidth(),
            menuOption = {
                Text(
                    modifier = Modifier,
                    text = it,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        )
    }

}
