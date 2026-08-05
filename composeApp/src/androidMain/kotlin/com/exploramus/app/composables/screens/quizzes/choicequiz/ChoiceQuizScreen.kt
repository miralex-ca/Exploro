package com.exploramus.app.composables.screens.quizzes.choicequiz

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.screens.quizzes.choicequiz.views.ChoiceQuizNavBar
import com.exploramus.app.composables.screens.quizzes.choicequiz.views.ChoiceQuizOptionsView
import com.exploramus.app.composables.screens.quizzes.choicequiz.views.ChoiceQuizQuestionView
import com.exploramus.app.composables.screens.quizzes.choicequiz.views.ChoiceQuizResultView
import com.exploramus.app.composables.screens.quizzes.choicequiz.views.ChoiceQuizTopBar
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isCompactHeight
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizContentType
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizItemState
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizState
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.toQuizResult
import kotlin.math.absoluteValue

@Composable
fun ChoiceQuizScreen(
    screenState: ChoiceQuizScreenState,
    eventHandler: ChoiceQuizEventHandler,
) {
    val rotation by animateFloatAsState(
        targetValue = screenState.totalRestartEvent * 180f,
        animationSpec = tween(durationMillis = 500),
        label = "ChoiceQuizRotation"
    )

    val isLandscape = LocalFormFactor.current.isLandscape

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ChoiceQuizTopBar(
            title = screenState.screenTitle,
            navigationMode = screenState.quiz.config.navigationMode,
            onBackClick = { eventHandler.onEvent(ChoiceQuizUiEvent.OnBackClicked) },
            onEvent = eventHandler::onEvent
        )

        if (screenState.isLoading) {
            ScreenLoading()
        } else {
            val quiz = screenState.quiz
            val pagerState = key(screenState.totalRestartEvent) {
                rememberPagerState(
                    initialPage = quiz.currentIndex,
                    pageCount = { quiz.items.size },
                )
            }

            // Sync with currentIndex for navigation
            LaunchedEffect(quiz.currentIndex) {
                if (pagerState.currentPage != quiz.currentIndex) {
                    pagerState.animateScrollToPage(quiz.currentIndex)
                }
            }

            LaunchedEffect(screenState.isFinished) {
                if (screenState.isFinished) {
                    eventHandler.onEvent(
                        ChoiceQuizUiEvent.OnSubmitQuizResult(screenState.quiz.toQuizResult())
                    )
                }
            }

            AnimatedContent(
                targetState = screenState.isFinished to screenState.totalRestartEvent,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) togetherWith
                            fadeOut(animationSpec = tween(200, delayMillis = 200))
                },
                label = "ChoiceQuizFlow",
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
            ) { (isFinished, restartEvent) ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            if (restartEvent % 2 != 0) {
                                if (isLandscape) rotationX = 180f else rotationY = 180f
                            }
                        }
                ) {
                    if (isFinished) {
                        ChoiceQuizResultView(
                            results = screenState.quiz.results,
                            onRestartClick = { eventHandler.onEvent(ChoiceQuizUiEvent.OnRestartClicked) },
                        )
                    } else {
                        ChoiceQuizContent(
                            quizState = quiz,
                            pagerState = pagerState,
                            onEvent = eventHandler::onEvent
                        )
                    }
                }
            }
        }
    }
}


private val NavBarReservedWidth = 72.dp

@Composable
fun ChoiceQuizContent(
    quizState: ChoiceQuizState,
    pagerState: PagerState,
    onEvent: (ChoiceQuizUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val isCompactLandscape = formFactor.isCompactHeight && formFactor.isLandscape

    val cardLayout = MaterialTheme.layout.flashcard

    val layout = MaterialTheme.layout.quiz


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = layout.maxHeight.value())
                .padding(top = layout.topPadding.value()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = cardLayout.cardHorizontalPadding.value()),
                pageSpacing = 32.dp,
                beyondViewportPageCount = 2,
                userScrollEnabled = false,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) { page ->
                val item = quizState.items[page]

                ChoiceQuizItem(
                    item = item,
                    isLandscape = isLandscape,
                    onOptionSelected = { optionId ->
                        onEvent(ChoiceQuizUiEvent.OnOptionSelected(item.id, optionId))
                    },
                    modifier = Modifier
                        .padding(
                            end = if (isCompactLandscape) NavBarReservedWidth else 0.dp,
                            bottom = if (isCompactLandscape) 25.dp else 30.dp,
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

            if (!isCompactLandscape) {
                val currentItem = quizState.items.getOrNull(pagerState.currentPage)
                ChoiceQuizNavBar(
                    results = quizState.results,
                    currentIndex = pagerState.currentPage,
                    totalCount = quizState.items.size,
                    isOptionSelected = currentItem?.selectedOptionId != null,
                    navigationMode = quizState.config.navigationMode,
                    onContinueClick = { onEvent(ChoiceQuizUiEvent.OnNextClicked) },
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = layout.bottomBarPadding.value())
                )
            }

        }

        if (isCompactLandscape) {
            val currentItem = quizState.items.getOrNull(pagerState.currentPage)

            ChoiceQuizNavBar(
                results = quizState.results,
                currentIndex = pagerState.currentPage,
                totalCount = quizState.items.size,
                isOptionSelected = currentItem?.selectedOptionId != null,
                navigationMode = quizState.config.navigationMode,
                onContinueClick = { onEvent(ChoiceQuizUiEvent.OnNextClicked) },
                isVertical = true,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = cardLayout.cardHorizontalPadding.value())
                    .padding(bottom = 4.dp)
                    .navigationBarsPadding()
            )
        }


    }
}

@Composable
fun ChoiceQuizItem(
    item: ChoiceQuizItemState,
    isLandscape: Boolean,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val layout = MaterialTheme.layout.quiz

    val cardsSpace = if (MaterialTheme.appColors.isDark) 2.dp else 1.dp

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth(),
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier.widthIn(max = layout.landScapeCardMaxWidth.value()),
                horizontalArrangement = Arrangement.spacedBy(cardsSpace)
            ) {
                ChoiceQuizQuestionView(
                    question = item.question,
                    shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
                    modifier = Modifier
                        .weight(0.7f)
                )
                ChoiceQuizOptionsView(
                    options = item.options,
                    selectedOptionId = item.selectedOptionId,
                    correctOptionId = item.correctOptionId,
                    isSubmitted = item.isSubmitted,
                    onOptionSelected = onOptionSelected,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                    useGridLayout = item.options.any { it.contentType == ChoiceQuizContentType.IMAGE },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .widthIn(max = layout.cardMaxWidth.value()),
                verticalArrangement = Arrangement.spacedBy(cardsSpace)
            ) {
                ChoiceQuizQuestionView(
                    question = item.question,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    modifier = Modifier
                        .weight(0.5f)
                )
                ChoiceQuizOptionsView(
                    options = item.options,
                    selectedOptionId = item.selectedOptionId,
                    correctOptionId = item.correctOptionId,
                    isSubmitted = item.isSubmitted,
                    onOptionSelected = onOptionSelected,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                    useGridLayout = item.options.any { it.contentType == ChoiceQuizContentType.IMAGE },
                    modifier = Modifier
                        .padding(bottom = layout.cardBottomPadding.value())
                        .weight(1f)
                        .fillMaxWidth(),
                )
            }
        }
    }
}
