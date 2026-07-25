package com.exploramus.app.composables.screens.quizzes.flashcards

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardHiddenHalf
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardOpenHalf
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardSettingsDialog
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardsTopBar
import com.exploramus.app.design.adaptive.HeightType
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.FlashcardStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardDeckState
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun FlashcardScreen(
    screenState: FlashcardScreenState,
    eventHandler: FlashcardEventHandler,
) {
    var activePagerState by remember { mutableStateOf<PagerState?>(null) }

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
            pagerState = if (screenState.isLoading) null else activePagerState
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
                LaunchedEffect(pagerState) {
                    if (revision == screenState.revision) {
                        activePagerState = pagerState
                    }
                }
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

@Composable
fun FlashcardContent(
    deckState: FlashcardDeckState,
    pagerState: PagerState,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape

    val layout = MaterialTheme.layout.flashcard

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = layout.maxHeight.value())
            .padding(top = layout.topPadding.value()),
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
                    .padding(bottom = 30.dp)
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

        if (formFactor.heightType != HeightType.COMPACT) {
            FlashcardNavBar(
                total = deckState.cards.size,
                pagerState = pagerState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = layout.bottomBarPadding.value())
            )
        }
    }
}


@Composable
fun FlashcardItem(
    card: FlashcardState,
    studyTarget: FlashcardStudyTarget,
    isRevealed: Boolean,
    isLandscape: Boolean,
    onRevealToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                    .fillMaxHeight(),
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

@Composable
fun FlashcardNavBar(
    total: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage

    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage((currentPage - 1).coerceAtLeast(0))
                    }
                },
                enabled = currentPage > 0,
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = Strings.commonPrevious,
                    tint = if (currentPage > 0)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                    modifier = Modifier.size(28.dp),
                )
            }

            Text(
                text = "${currentPage + 1} / $total",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .padding(horizontal = 8.dp)
            )

            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage((currentPage + 1).coerceAtMost(total - 1))
                    }
                },
                enabled = currentPage < total - 1,
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = Strings.commonNext,
                    tint = if (currentPage < total - 1)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                    modifier = Modifier.size(28.dp),
                )
            }
        }
    }
}
