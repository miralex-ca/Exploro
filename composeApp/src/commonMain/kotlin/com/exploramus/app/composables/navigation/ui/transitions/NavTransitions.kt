package com.exploramus.app.composables.navigation.ui.transitions

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

private const val NAV_TRANSITION_DURATION_MS = 350

private fun holdThenSnapOut(durationMs: Int): ExitTransition =
    fadeOut(animationSpec = tween(durationMillis = 1, delayMillis = durationMs))

fun pushTransition(durationMs: Int = NAV_TRANSITION_DURATION_MS): ContentTransform =
    ContentTransform(
        targetContentEnter = fadeIn(animationSpec = tween(durationMs)),
        initialContentExit = holdThenSnapOut(durationMs),
        sizeTransform = SizeTransform { _, _ -> tween(durationMs) }
    )

fun popTransition(durationMs: Int = NAV_TRANSITION_DURATION_MS): ContentTransform =
    ContentTransform(
        targetContentEnter = EnterTransition.None,
        initialContentExit = fadeOut(animationSpec = tween(durationMs)),
        sizeTransform = SizeTransform { _, _ -> tween(durationMs) }
    )


private const val NAV_RAIL_APPEAR_DELAY_MS = 120
private const val NAV_RAIL_APPEAR_DURATION_MS = 220

val navRailEnterTransition: EnterTransition =
    fadeIn(animationSpec = tween(durationMillis = NAV_RAIL_APPEAR_DURATION_MS, delayMillis = NAV_RAIL_APPEAR_DELAY_MS))

val navRailExitTransition: ExitTransition =
    fadeOut(animationSpec = tween(NAV_RAIL_APPEAR_DURATION_MS))

val bottomBarEnterTransition: EnterTransition =
    slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(120)) +
            fadeIn(initialAlpha = 0.2f, animationSpec = tween(100))

val bottomBarExitTransition: ExitTransition =
    fadeOut(animationSpec = tween(250, delayMillis = 80))