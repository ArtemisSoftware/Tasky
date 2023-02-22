package com.artemissoftware.core_ui.composables.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.artemissoftware.core_ui.composables.animations.TYLottieLoader
import com.artemissoftware.core_ui.composables.button.TYButton
import com.artemissoftware.core_ui.composables.text.TYText
import com.artemissoftware.core_ui.theme.White

@Composable
fun TYDialog(
    title: String,
    description: String? = null,
    buttonDescription: String,
    onDismiss: () -> Unit,
    iconSize: Dp = 300.dp
) {

    val ratioTop = (iconSize.value * 2 / 3).dp
    val ratioTopText = (iconSize.value * 10 / 75).dp

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Box(modifier = Modifier.wrapContentSize()) {

            ConstraintLayout {

                val (lottie, content) = createRefs()

                DialogContent(
                    modifier = Modifier
                        .constrainAs(content){
                            top.linkTo(parent.top, margin = ratioTop)
                        },
                    ratioTopText = ratioTopText,
                    title = title,
                    description = description,
                    buttonDescription = buttonDescription,
                    onClick = onDismiss
                )

                TYLottieLoader(
                    modifier = Modifier
                        .size(iconSize)
                        .constrainAs(lottie) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

            }
        }
    }

}


@Composable
private fun DialogContent(
    modifier: Modifier = Modifier,
    ratioTopText: Dp,
    title: String,
    description: String? = null,
    buttonDescription: String,
    onClick: () -> Unit,
) {

    Column(modifier = modifier) {

        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = White,
                    shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .padding(top = ratioTopText),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                TextCaption(title = title, description = description)

                Spacer(modifier = Modifier.height(24.dp))

                TYButton(
                    text = buttonDescription,
                    onClick = onClick
                )

                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


@Composable
private fun TextCaption(
    title: String,
    description: String? = null,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TYText(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )

        description?.let {
            TYText(
                text = description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
        }

    }

}




@Preview(showBackground = true)
@Composable
private fun TYDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        TYDialog(
            onDismiss = {},
            title = "title",
            description = "description",
            buttonDescription = "Your Button Message",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogContentPreview() {
    DialogContent(
        title = "title",
        description = "description",
        buttonDescription = "Your Button Message",
        onClick = {},
        ratioTopText = 200.dp
    )
}

@Preview(showBackground = true)
@Composable
private fun TextCaptionPreview() {
    TextCaption(title = "title", description = "description")
}

