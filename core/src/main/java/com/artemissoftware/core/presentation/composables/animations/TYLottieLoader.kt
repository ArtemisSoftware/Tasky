package com.artemissoftware.core_ui.composables.animations

import androidx.compose.runtime.Composable
import com.artemissoftware.core_ui.R
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun TYLottieLoader(
    @RawRes id: Int = R.raw.example_lottie,
    modifier: Modifier = Modifier,
    iterateForever: Boolean = false
) {

    val compositionResult: LottieCompositionResult =
        rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(id)
        )

    LottieAnimation(
        composition = compositionResult.value,
        isPlaying  = true,
        iterations = if(iterateForever) LottieConstants.IterateForever else 1,
        speed = 1.5f,
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
private fun TYLottieLoaderPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TYLottieLoader(
            id = R.raw.example_lottie,
            modifier = Modifier
                .size(300.dp)
        )
        TYLottieLoader(
            id = R.raw.example_lottie,
            iterateForever = true,
            modifier = Modifier.size(300.dp)
        )
    }
}
