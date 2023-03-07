package com.artemissoftware.tasky.util

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween

object VisibilityTransitions {

    fun enterEdition(fadeInDurationMillis: Int = 500, expandHorizontallyDurationMillis: Int = 1000): EnterTransition {
        return fadeIn(animationSpec = tween(fadeInDurationMillis)) +
                expandHorizontally  (
                    animationSpec = tween(expandHorizontallyDurationMillis,
                        easing = LinearEasing
                    )
                )
    }

    fun exitEdition(fadeOutDurationMillis: Int = 500, shrinkHorizontallyDurationMillis: Int = 1000): ExitTransition {
        return fadeOut(animationSpec = tween(fadeOutDurationMillis)) +
                shrinkHorizontally (
                    animationSpec = tween(shrinkHorizontallyDurationMillis,
                        easing = LinearEasing)
                )
    }

}