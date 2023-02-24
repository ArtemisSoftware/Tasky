package com.artemissoftware.core_ui.composables.dialog

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
import com.artemissoftware.core_ui.R
import com.artemissoftware.core_ui.composables.animations.TYLottieLoader
import com.artemissoftware.core_ui.composables.button.TYTextButton
import com.artemissoftware.core_ui.composables.scaffold.TYScaffoldState
import com.artemissoftware.core_ui.composables.text.TYText


@Composable
fun TYDialog(tyScaffoldState: TYScaffoldState) {

    tyScaffoldState.dialog.value?.let { dialogType->

        Dialog(
            onDismissRequest = { },
            content = {
                TYDialogContent(
                    tyScaffoldState = tyScaffoldState,
                    dialogType = dialogType
                )
            }
        )
    }
}




@Composable
private fun TYDialogContent(
    tyScaffoldState: TYScaffoldState,
    dialogType: TYDialogType
){

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 5.dp, bottom = 20.dp),
        elevation = 8.dp
    ) {
        Column {

            ResourceContent(dialogType = dialogType).invoke()

            DialogMessage(dialogType = dialogType)

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            DialogOptions(
                tyScaffoldState = tyScaffoldState,
                dialogOptions = dialogType.dialogOptions
            )
        }
    }
}




@Preview
@Composable
private fun TYDialogContentPreview(){

    val dialogTypeSuccess = TYDialogType.Success(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TYDialogOptions(
            confirmationTextId = R.string.ok,
            cancelTextId = R.string.cancel
        )
    )

    val dialogTypError = TYDialogType.Error(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TYDialogOptions(
            confirmationTextId = R.string.ok,
        )
    )

    Column {
        TYDialogContent(dialogType = dialogTypeSuccess, tyScaffoldState = TYScaffoldState())
        TYDialogContent(dialogType = dialogTypError, tyScaffoldState = TYScaffoldState())
    }
}



@Composable
private fun ResourceContent(
    dialogType: TYDialogType
): @Composable () -> Unit {
    val resourceContent: @Composable () -> Unit = when {

        dialogType.lottieId != null -> {
            {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TYLottieLoader(
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
private fun DialogMessage(dialogType: TYDialogType){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TYText(
            text = dialogType.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        TYText(
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

    val dialogTypeSuccess = TYDialogType.Success(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TYDialogOptions(
            confirmationTextId = R.string.ok,
            cancelTextId = R.string.cancel
        )
    )

    DialogMessage(dialogType = dialogTypeSuccess)
}





@Composable
private fun DialogOptions(
    tyScaffoldState: TYScaffoldState,
    dialogOptions: TYDialogOptions
){

    val confirmModifier = if(dialogOptions.getOptionsType() == TYDialogButtonType.DOUBLE_OPTION) Modifier else Modifier.fillMaxWidth()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        if(dialogOptions.getOptionsType() == TYDialogButtonType.DOUBLE_OPTION){
            TYTextButton(
                text = stringResource(id = dialogOptions.cancelTextId ?: R.string.cancel),
                onClick = {
                    dialogOptions.cancel()
                    tyScaffoldState.closeDialog()
                }
            )
        }

        TYTextButton(
            modifier = confirmModifier,
            text = stringResource(id = dialogOptions.confirmationTextId),
            onClick = {
                dialogOptions.confirmation()
                tyScaffoldState.closeDialog()
            }
        )
    }
}


@Preview
@Composable
private fun DialogOptionsPreview(){

    val dialogTypeSuccess = TYDialogType.Success(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TYDialogOptions(
            confirmationTextId = R.string.ok,
            cancelTextId = R.string.cancel
        )
    )

    val dialogTypError = TYDialogType.Error(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        dialogOptions = TYDialogOptions(
            confirmationTextId = R.string.ok,
        )
    )

    Column {
        DialogOptions(
            tyScaffoldState = TYScaffoldState(),
            dialogOptions = dialogTypeSuccess.dialogOptions
        )
        DialogOptions(
            tyScaffoldState = TYScaffoldState(),
            dialogOptions = dialogTypError.dialogOptions
        )
    }
}


