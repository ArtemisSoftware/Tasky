package com.artemissoftware.tasky.authentication.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.presentation.composables.TaskyContentSurface
import com.artemissoftware.core.presentation.composables.button.TaskyButton
import com.artemissoftware.core.presentation.composables.button.TaskyTextButton
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.composables.text.TaskyText
import com.artemissoftware.core.presentation.composables.textfield.TaskyOutlinedTextField
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldType
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.Link
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.Greeting
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.ui.theme.TaskyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen() {

    TaskyScaffold(
        backgroundColor = Black,
        content = {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TaskyText(
                    modifier = Modifier
                        .padding(vertical = 48.dp),
                    color = White,
                    style = MaterialTheme.typography.h4,
                    text = stringResource(id = R.string.welcome_back)
                )

                TaskyContentSurface(
                    content = {
                        Box(
                            modifier = Modifier
                                .padding(top = 48.dp)
                                .padding(bottom = 68.dp)
                                .padding(horizontal = 16.dp)
                        ){

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter)
                            ) {

                                TaskyOutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    hint = stringResource(id = R.string.email_address),
                                    text = ""
                                )

                                TaskyOutlinedTextField(
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    taskyTextFieldType = TaskyTextFieldType.PASSWORD,
                                    hint = stringResource(id = R.string.password),
                                    text = ""
                                )

                                TaskyButton(
                                    modifier = Modifier
                                        .padding(top = 24.dp)
                                        .fillMaxWidth()
                                        .height(52.dp),
                                    text = stringResource(id = R.string.log_in),
                                    onClick = {  }
                                )
                            }

                            
                            TaskyTextButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
                                allCaps = true,
                                text = buildAnnotatedString {
                                    append(stringResource(id = R.string.dont_have_an_account))
                                    append(" ")
                                    withStyle(
                                        style = SpanStyle(
                                            color = Link
                                        )
                                    ) {
                                        append(stringResource(id = R.string.sign_up))
                                    }
                                },
                                onClick = {}
                            )
                        }
                    }
                )
            }



        }
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}