package com.artemissoftware.tasky.authentication.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.icon.TaskyIcon
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffold
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.White

@Composable
fun SplashScreen(viewModel: SplashViewModel /* TODO : init viewmodel with Hilt when dependency is included on the project */) {

    SplashScreenContent(
    )
}

@Composable
private fun SplashScreenContent() {

    TaskyScaffold(
        backgroundColor = Green,
        content = {

            Box(modifier = Modifier.fillMaxSize()){
                
                TaskyIcon(
                    modifier = Modifier.align(Alignment.Center),
                    color = White,
                    size = 140.dp,
                    icon = R.drawable.ic_tasky_logo
                )

            }
        }
    )

}

@Preview(showBackground = true)
@Composable
private fun SplashScreenContentPreview() {
    SplashScreenContent()
}