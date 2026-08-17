package com.exploramus.app.composables.navigation.ui.transitions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable

enum class ScreenTransitionStyle {
    PLATFORM_DEFAULT,
    CUSTOM,
    IOS,
    IOS_TABLET
}

@Composable
expect fun currentScreenTransitionStyle(): ScreenTransitionStyle

data class ScreenTransitions(
    val push: (AnimatedContentTransitionScope<*>.() -> ContentTransform)? = null,
    val pop: (AnimatedContentTransitionScope<*>.() -> ContentTransform)? = null,
    val predictivePop: (AnimatedContentTransitionScope<*>.(Int) -> ContentTransform)? = null,
)

@Composable
fun rememberScreenTransitions(style: ScreenTransitionStyle): ScreenTransitions? = when (style) {
    ScreenTransitionStyle.CUSTOM -> ScreenTransitions(
        push = { pushTransition() },
        pop = { popTransition() },
        predictivePop = {
            popTransition()
        }
    )
    ScreenTransitionStyle.IOS -> ScreenTransitions(
        push = { iosPushTransition() }
    )
    ScreenTransitionStyle.IOS_TABLET -> ScreenTransitions(
        push = { pushTransition() },
        pop = { popTransition() },
        predictivePop = { edge ->
            tabletPredictivePopTransition(edge)
        }
    )
    ScreenTransitionStyle.PLATFORM_DEFAULT -> null
}
