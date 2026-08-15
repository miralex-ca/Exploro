package com.exploramus.app.composables.screens.quizzes.flashcards

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardHiddenHalf
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardNavBar
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardOpenHalf
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardSettingsDialog
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardsTopBar
import com.exploramus.app.design.adaptive.*
import com.exploramus.app.design.theme.appColors
import com.exploramus.core.models.FlashcardStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardDeckState
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardState
import kotlin.math.absoluteValue

@Composable
fun FlashcardScreen(
    screenState: FlashcardScreenState,
    eventHandler: FlashcardEventHandler,
) {
    val rotation by animateFloatAsState(
        targetValue = screenState.revision * 180f,
        animationSpec = tween(durationMillis = 500),
        label = "FlashcardRotation"
    )

    val isLandscape = LocalFormFactor.current.isLandscape

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        FlashcardsTopBar(
            title = screenState.screenTitle,
            onBackClick = { eventHandler.onEvent(FlashcardUiEvent.OnBackClicked) },
            onEvent = eventHandler::onEvent,
        )

        if (screenState.isLoading) {
            ScreenLoading()
        } else {
            AnimatedContent(
                targetState = screenState.deck to screenState.revision,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) togetherWith
                            fadeOut(animationSpec = tween(200, delayMillis = 200))
                },
                label = "FlashcardRestart",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (isLandscape) {
                            rotationX = rotation
                        } else {
                            rotationY = rotation
                        }
                        cameraDistance = 12f * density
                    }
            ) { (deck, revision) ->
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { deck.cards.size },
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            if (revision % 2 != 0) {
                                if (isLandscape) rotationX = 180f else rotationY = 180f
                            }
                        }
                ) {
                    FlashcardContent(
                    deckState = deck,
                    pagerState = pagerState
                )
                }
            }
        }
    }

    if (screenState.isSettingsDialogVisible) {
        FlashcardSettingsDialog(
            currentConfig = screenState.deck.config,
            onConfigChanged = { eventHandler.onEvent(FlashcardUiEvent.OnConfigChanged(it)) },
            onDismiss = { eventHandler.onEvent(FlashcardUiEvent.OnSettingsDismissed) },
        )
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun FlashcardContent(
    deckState: FlashcardDeckState,
    pagerState: PagerState,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val isCompactLandscape = formFactor.isCompactHeight && formFactor.isLandscape

    val layout = MaterialTheme.layout.flashcard

    val windowHeight = LocalConfiguration.current.screenHeightDp
    val topPadding = if (!isCompactLandscape && isLandscape && windowHeight < 620 ) 30.dp
    else layout.topPadding.value()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = layout.maxHeight.value())
                .padding(top = topPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = layout.cardHorizontalPadding.value()),
                pageSpacing = 32.dp,
                beyondViewportPageCount = 2,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) { page ->
                val card = deckState.cards[page]
                var isRevealed by retain(page) { mutableStateOf(deckState.config.revealEnabled) }

                FlashcardItem(
                    card = card,
                    studyTarget = deckState.config.studyTarget,
                    isRevealed = isRevealed,
                    isLandscape = isLandscape,
                    onRevealToggle = { isRevealed = !isRevealed },
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            end = if (isCompactLandscape) NavBarReservedWidth else 0.dp,
                            bottom = 30.dp
                        )
                        .graphicsLayer {
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            val scale = lerp(
                                start = 0.94f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                            scaleX = scale
                            scaleY = scale
                            alpha = lerp(
                                start = 0.8f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                )
            }

            if (!isCompactLandscape && formFactor.heightType != HeightType.COMPACT) {
                FlashcardNavBar(
                    total = deckState.cards.size,
                    pagerState = pagerState,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = layout.bottomBarPadding.value())
                )
            }
        }

        if (isCompactLandscape) {
            FlashcardNavBar(
                total = deckState.cards.size,
                pagerState = pagerState,
                isVertical = true,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = layout.cardHorizontalPadding.value())
                    .padding(bottom = 4.dp)
                    .navigationBarsPadding()
            )
        }
    }
}

private val NavBarReservedWidth = 72.dp

@Composable
fun FlashcardItem(
    card: FlashcardState,
    studyTarget: FlashcardStudyTarget,
    isRevealed: Boolean,
    isLandscape: Boolean,
    onRevealToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val formFactor = LocalFormFactor.current
    val layout = MaterialTheme.layout.flashcard
    val cardsSpace = if (MaterialTheme.appColors.isDark) 2.dp else 1.dp

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth(),
    ) {
        if (isLandscape) {
            val openCardRatio = if (studyTarget == FlashcardStudyTarget.IMAGE) 0.8f else 0.65f
            Row(
                modifier = Modifier
                    .widthIn(max = layout.landScapeCardMaxWidth.value())
                    .then(
                        if (formFactor.isCompactHeight) {
                            Modifier.heightIn(max = 360.dp)
                        } else {
                            Modifier.fillMaxHeight()
                        }
                    ),
                horizontalArrangement = Arrangement.spacedBy(cardsSpace)
            ) {
                FlashcardOpenHalf(
                    card = card,
                    studyTarget = studyTarget,
                    shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
                    modifier = Modifier
                        .weight(openCardRatio)
                        .fillMaxHeight()
                )
                FlashcardHiddenHalf(
                    card = card,
                    revealField = studyTarget,
                    isRevealed = isRevealed,
                    isLandscape = isLandscape,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                    onToggle = onRevealToggle,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .widthIn(max = layout.cardMaxWidth.value())
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(cardsSpace)
            ) {
                val openCardRatio = if (studyTarget == FlashcardStudyTarget.IMAGE) 0.65f else 0.4f
                FlashcardOpenHalf(
                    card = card,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    modifier = Modifier
                        .weight(openCardRatio)
                        .fillMaxWidth(),
                    studyTarget = studyTarget,
                )

                FlashcardHiddenHalf(
                    card = card,
                    revealField = studyTarget,
                    isRevealed = isRevealed,
                    isLandscape = isLandscape,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                    onToggle = onRevealToggle,
                    modifier = Modifier
                        .padding(bottom = layout.cardBottomPadding.value())
                        .weight(1f)
                        .fillMaxWidth(),
                )
            }
        }
    }
}
