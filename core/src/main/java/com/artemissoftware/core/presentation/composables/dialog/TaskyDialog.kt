package com.artemissoftware.core.presentation.composables.dialog

import androidx.annotation.RawRes
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


@Composable
fun TaskyDialog(
    taskyDialogType: TaskyDialogType,
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = { },
        content = {
            TaskyDialogContent(
                modifier = modifier,
                onDialogDismiss = onDialogDismiss,
                taskyDialogType = taskyDialogType
            )
        }
    )
}




@Composable
private fun TaskyDialogContent(
    taskyDialogType: TaskyDialogType,
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier
){

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(horizontal = 12.dp)
            .padding(top = 5.dp, bottom = 20.dp),
        elevation = 8.dp
    ) {
        Column {

            taskyDialogType.lottieId?.let {
                ResourceContent(
                    modifier = Modifier.fillMaxWidth(),
                    lottieId = it
                )
            }

            DialogMessage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                dialogType = taskyDialogType)

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            DialogOptions(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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
        dialogOptions = TaskyDialogOptions.DoubleOption(
            confirmationText = R.string.ok,
            cancelText = R.string.cancel
        )
    )

    val dialogTypError = TaskyDialogType.Error(
        title = "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TaskyDialogOptions.SingleOption(
            confirmationText = R.string.ok,
        )
    )

    Column {
        TaskyDialogContent(taskyDialogType = dialogTypeSuccess, onDialogDismiss = {})
        TaskyDialogContent(taskyDialogType = dialogTypError, onDialogDismiss = {})
    }
}



@Composable
private fun ResourceContent(
    @RawRes lottieId: Int,
    modifier: Modifier = Modifier

) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        TaskyLottieLoader(
            id = lottieId,
            iterateForever = false,
            modifier = Modifier
                .size(120.dp)
                .padding(top = 16.dp)
        )
    }
}



@Composable
private fun DialogMessage(
    dialogType: TaskyDialogType,
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier
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
        dialogOptions = TaskyDialogOptions.DoubleOption(
            confirmationText = R.string.ok,
            cancelText = R.string.cancel
        )
    )

    DialogMessage(dialogType = dialogTypeSuccess)
}





@Composable
private fun DialogOptions(
    onDialogDismiss: () -> Unit,
    dialogOptions: TaskyDialogOptions,
    modifier: Modifier = Modifier
){

    val confirmModifier = when(dialogOptions){

        is TaskyDialogOptions.SingleOption ->{
            Modifier.fillMaxWidth()
        }
        is TaskyDialogOptions.DoubleOption ->{
            Modifier
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        if(dialogOptions is TaskyDialogOptions.DoubleOption){
            TaskyTextButton(
                text = stringResource(id = dialogOptions.cancelText ?: R.string.cancel),
                onClick = {
                    dialogOptions.cancel()
                    onDialogDismiss.invoke()
                }
            )
        }

        TaskyTextButton(
            modifier = confirmModifier,
            text = stringResource(id = dialogOptions.optionText),
            onClick = {
                dialogOptions.confirmationOption()
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
        dialogOptions = TaskyDialogOptions.DoubleOption(
            confirmationText = R.string.ok,
            cancelText = R.string.cancel
        )
    )

    val dialogTypError = TaskyDialogType.Error(
        title = "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TaskyDialogOptions.SingleOption(
            confirmationText = R.string.ok,
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


