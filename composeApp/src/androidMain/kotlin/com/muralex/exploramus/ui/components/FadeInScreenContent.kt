package com.muralex.exploramus.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun FadeInScreenContent(
    durationMillis: Int = 320,
    content: @Composable () -> Unit
) {
    val state = remember {
        MutableTransitionState(false).apply { targetState = true }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(animationSpec = tween(durationMillis)),
        exit = fadeOut(),
    ) {
        content()
    }
}


@Composable
fun FadeLoadingContent(
    isLoading: Boolean,
    loadingContent: @Composable () -> Unit = { ScreenLoading() },
    content: @Composable () -> Unit
) {
    AnimatedContent(
        targetState = isLoading,
        transitionSpec = {
            fadeIn(tween(200)) togetherWith fadeOut(tween(100))
        },
        label = "loading_content"
    ) { loading ->
        if (loading) loadingContent() else content()
    }
}