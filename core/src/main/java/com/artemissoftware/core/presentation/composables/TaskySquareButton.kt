package com.artemissoftware.core.presentation.composables.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.presentation.theme.White

@Composable
fun TaskySquareButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    size: Dp = 56.dp,
    iconColor: Color = White,
    backgroundColor: Color = Black,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .size(size),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = iconColor,
            modifier = Modifier,
            contentDescription = "",
        )
    }
}

@Composable
fun <T>TaskyExpandableSquareButton(
    @DrawableRes icon: Int,
    options: List<T>,
    onOptionSelected: (T) -> Unit,
    menuOption: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    addDivider: Boolean = false,
    size: Dp = 56.dp,
    iconColor: Color = White,
    backgroundColor: Color = Black,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End,
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

        TaskySquareButton(
            size = size,
            iconColor = iconColor,
            backgroundColor = backgroundColor,
            modifier = Modifier.padding(top = 12.dp),
            icon = icon,
            onClick = {
                expanded = !expanded
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskySquareButtonPreview() {
    TaskySquareButton(icon = R.drawable.ic_add, onClick = {})
}
