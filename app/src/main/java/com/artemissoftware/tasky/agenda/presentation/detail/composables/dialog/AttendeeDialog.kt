package com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.artemissoftware.core.presentation.composables.button.TaskyButton
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.composables.textfield.TaskyOutlinedTextField
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.tasky.R
import com.artemissoftware.core.R as CoreR

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttendeeDialog(
    onCloseClick: () -> Unit,
    onAddClick: (String) -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = { },
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(size = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(all = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        TaskyIcon(
                            modifier = Modifier
                                .clickable {
                                    onCloseClick()
                                },
                            icon = CoreR.drawable.ic_close,
                            color = Black,
                        )
                    }

                    TaskyText(
                        style = MaterialTheme.typography.h6,
                        text = stringResource(id = R.string.add_visitor),
                    )
                    TaskyOutlinedTextField(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        text = email,
                        onValueChange = {
                            email = it
                        },
                        hint = stringResource(id = R.string.email_address),
                    )

                    TaskyButton(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth()
                            .height(55.dp),
                        text = stringResource(id = R.string.add),
                        onClick = {
                            onAddClick(email)
                        },
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TAttendeeDialogPreview() {
    AttendeeDialog(
        onCloseClick = {}, onAddClick = {}
    )
}
