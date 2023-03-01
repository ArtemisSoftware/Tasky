package com.artemissoftware.core.presentation.composables.loading

import androidx.annotation.RawRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.animations.TaskyLottieLoader
import com.artemissoftware.core.presentation.theme.LoadingBackground

@Composable
fun TaskyLoading(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    @RawRes lottieId: Int = R.raw.loading_lottie,
) {

    AnimatedVisibility(
        modifier = modifier,
        visible = isLoading
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = false, onClick = {})
                .background(MaterialTheme.colors.LoadingBackground.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {

            TaskyLottieLoader(
                iterateForever = true,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 40.dp),
                id = lottieId
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskyLoadingPreview() {
    TaskyLoading(isLoading = true, modifier = Modifier.fillMaxSize())
}