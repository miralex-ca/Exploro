package com.exploramus.app.composables.screens.quizzes.choicequiz

import androidx.compose.animation.AnimatedContent
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
import com.exploramus.app.design.adaptive.HeightType
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizContentType
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizItemState
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizState
import kotlin.math.absoluteValue

@Composable
fun ChoiceQuizScreen(
    screenState: ChoiceQuizScreenState,
    eventHandler: ChoiceQuizEventHandler,
) {
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

            AnimatedContent(
                targetState = screenState.isFinished to screenState.totalRestartEvent,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) togetherWith
                            fadeOut(animationSpec = tween(200, delayMillis = 200))
                },
                label = "ChoiceQuizFlow",
                modifier = Modifier.fillMaxSize()
            ) { (isFinished, restartEvent) ->
                if (isFinished) {
                    ChoiceQuizResultView(
                        results = screenState.quiz.results,
                        onRestartClick = { eventHandler.onEvent(ChoiceQuizUiEvent.OnRestartClicked) },
                        modifier = Modifier.fillMaxSize()
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

@Composable
fun ChoiceQuizContent(
    quizState: ChoiceQuizState,
    pagerState: PagerState,
    onEvent: (ChoiceQuizUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape

    val layout = MaterialTheme.layout.flashcard // Reuse flashcard layout values for consistency

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
}

@Composable
fun ChoiceQuizItem(
    item: ChoiceQuizItemState,
    isLandscape: Boolean,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val layout = MaterialTheme.layout.flashcard

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth(),
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .widthIn(max = layout.landScapeCardMaxWidth.value())
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ChoiceQuizQuestionView(
                    question = item.question,
                    shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
                    modifier = Modifier
                        .weight(0.65f)
                        .fillMaxHeight()
                )
                ChoiceQuizOptionsView(
                    options = item.options,
                    selectedOptionId = item.selectedOptionId,
                    correctOptionId = item.correctOptionId,
                    isSubmitted = item.isSubmitted,
                    onOptionSelected = onOptionSelected,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                    useGridLayout = item.question.contentType == ChoiceQuizContentType.IMAGE || item.options.any { it.contentType == ChoiceQuizContentType.IMAGE },
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
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ChoiceQuizQuestionView(
                    question = item.question,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxWidth(),
                )
                ChoiceQuizOptionsView(
                    options = item.options,
                    selectedOptionId = item.selectedOptionId,
                    correctOptionId = item.correctOptionId,
                    isSubmitted = item.isSubmitted,
                    onOptionSelected = onOptionSelected,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                    useGridLayout = item.question.contentType == ChoiceQuizContentType.IMAGE || item.options.any { it.contentType == ChoiceQuizContentType.IMAGE },
                    modifier = Modifier
                        .padding(bottom = layout.cardBottomPadding.value())
                        .weight(1f)
                        .fillMaxWidth(),
                )
            }
        }
    }
}
