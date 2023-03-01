package com.artemissoftware.core.presentation.composables.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.animations.TaskyLottieLoader
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core_ui.composables.dialog.TaskyDialogType


@Composable
fun TaskyDialog(
    taskyDialogType: TaskyDialogType? = null,
    onDialogDismiss: () -> Unit
) {

    taskyDialogType?.let { dialogType->

        Dialog(
            onDismissRequest = { },
            content = {
                TaskyDialogContent(
                    onDialogDismiss = onDialogDismiss,
                    taskyDialogType = dialogType
                )
            }
        )
    }
}




@Composable
private fun TaskyDialogContent(
    taskyDialogType: TaskyDialogType,
    onDialogDismiss: () -> Unit
){

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 5.dp, bottom = 20.dp),
        elevation = 8.dp
    ) {
        Column {

            ResourceContent(dialogType = taskyDialogType).invoke()

            DialogMessage(dialogType = taskyDialogType)

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            DialogOptions(
                onDialogDismiss = onDialogDismiss,
                dialogOptions = taskyDialogType.dialogOptions
            )
        }
    }
}




@Preview
@Composable
private fun TaskyDialogContentPreview(){

    val dialogTypeSuccess = TaskyDialogType.Success(
        title = "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TaskyDialogOptions(
            confirmationTextId = R.string.ok,
            cancelTextId = R.string.cancel
        )
    )

    val dialogTypError = TaskyDialogType.Error(
        title = "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TaskyDialogOptions(
            confirmationTextId = R.string.ok,
        )
    )

    Column {
        TaskyDialogContent(taskyDialogType = dialogTypeSuccess, onDialogDismiss = {})
        TaskyDialogContent(taskyDialogType = dialogTypError, onDialogDismiss = {})
    }
}



@Composable
private fun ResourceContent(
    dialogType: TaskyDialogType
): @Composable () -> Unit {
    val resourceContent: @Composable () -> Unit = when {

        dialogType.lottieId != null -> {
            {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TaskyLottieLoader(
                        id = dialogType.lottieId,
                        iterateForever = false,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(top = 16.dp)
                    )
                }

            }
        }
        else -> {
            {}
        }
    }
    return resourceContent
}



@Composable
private fun DialogMessage(dialogType: TaskyDialogType){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TaskyText(
            text = dialogType.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        TaskyText(
            text = dialogType.description,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
        )
    }
}


@Preview
@Composable
private fun DialogMessagePreview(){

    val dialogTypeSuccess = TaskyDialogType.Success(
        title = "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TaskyDialogOptions(
            confirmationTextId = R.string.ok,
            cancelTextId = R.string.cancel
        )
    )

    DialogMessage(dialogType = dialogTypeSuccess)
}





@Composable
private fun DialogOptions(
    onDialogDismiss: () -> Unit,
    dialogOptions: TaskyDialogOptions
){

    val confirmModifier = if(dialogOptions.getOptionsType() == TaskyDialogButtonType.DOUBLE_OPTION) Modifier else Modifier.fillMaxWidth()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        if(dialogOptions.getOptionsType() == TaskyDialogButtonType.DOUBLE_OPTION){
            TaskyTextButton(
                text = stringResource(id = dialogOptions.cancelTextId ?: R.string.cancel),
                onClick = {
                    dialogOptions.cancel()
                    onDialogDismiss.invoke()
                }
            )
        }

        TaskyTextButton(
            modifier = confirmModifier,
            text = stringResource(id = dialogOptions.confirmationTextId),
            onClick = {
                dialogOptions.confirmation()
                onDialogDismiss.invoke()
            }
        )
    }
}


@Preview
@Composable
private fun DialogOptionsPreview(){

    val dialogTypeSuccess = TaskyDialogType.Success(
        title = "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TaskyDialogOptions(
            confirmationTextId = R.string.ok,
            cancelTextId = R.string.cancel
        )
    )

    val dialogTypError = TaskyDialogType.Error(
        title = "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TaskyDialogOptions(
            confirmationTextId = R.string.ok,
        )
    )

    Column {
        DialogOptions(
            onDialogDismiss = {},
            dialogOptions = dialogTypeSuccess.dialogOptions
        )
        DialogOptions(
            onDialogDismiss = {},
            dialogOptions = dialogTypError.dialogOptions
        )
    }
}


