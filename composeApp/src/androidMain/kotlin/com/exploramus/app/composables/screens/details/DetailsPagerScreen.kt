package com.exploramus.app.composables.screens.details


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.FadeInScreenContent
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.navigation.ui.topbars.DetailsPagerTopBar
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.details.CountryDetailsState
import com.exploramus.shared.viewmodel.screens.details.detailpager.DetailsPagerController
import com.exploramus.shared.viewmodel.screens.details.detailpager.DetailsPagerScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetailsPagerScreen(
    screenState: DetailsPagerScreenState,
    eventHandler: DetailsPagerEventHandler
) {
    val details = screenState.details

    FadeInScreenContent {
        when {
            screenState.isLoading -> {
                ScreenLoading()
            }

            details == null -> {
                EmptyStateView(EmptyState.NotFound)
            }

            else -> {
                DetailsPagerScreenContent(
                    screenState = screenState,
                    details = details,
                    onEvent = eventHandler::onEvent
                )
            }
        }
    }
}

@Composable
fun DetailsPagerScreenContent(
    screenState: DetailsPagerScreenState,
    details: CountryDetailsState,
    onEvent: (DetailsPagerUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val layout = MaterialTheme.layout.details

    val pagerController = screenState.pagerController
    val composePagerController = rememberComposePagerController(pagerController)
    val pagerState = composePagerController.pagerState

    // Fire OnPageChanged after swipe settles
    LaunchedEffect(pagerState.settledPage) {
        val settled = pagerState.settledPage
        if (settled != pagerController.currentIndex) {
            onEvent(DetailsPagerUiEvent.OnPageChanged(settled))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            DetailsPagerTopBar(
                title = details.name,
                mapsUrl = details.mapsUrl,
                wikiUrl = details.wikiUrl,
                onBackClick = { onEvent(DetailsPagerUiEvent.OnBackClicked) },
                onPreviousClick = {
                    onEvent(DetailsPagerUiEvent.OnPreviousClicked)
                    composePagerController.animateToPrevious()
                },
                onNextClick = {
                    onEvent(DetailsPagerUiEvent.OnNextClicked)
                    composePagerController.animateToNext()
                },
                hasPrevious = pagerController.hasPrevious,
                hasNext = pagerController.hasNext,
            )

            PagerDotsIndicator(
                totalCount = pagerController.totalCount,
                currentIndex = pagerController.currentIndex,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = layout.maxWidth.value())
                            .padding(horizontal = layout.horizontalPadding.value())
                            .padding(
                                top = layout.topPadding.value(),
                                bottom = layout.bottomPadding.value()
                            )
                    ) {
                        if (isLandscape) {
                            LargeDetailsSections(
                                details = details,
                                onFavoriteClick = {
                                  //  onEvent(DetailsPagerUiEvent.ToggleFavorite())
                                }
                            )
                        } else {
                            DetailsSections(
                                details = details,
                                onFavoriteClick = {
                                   // onEvent(DetailsPagerUiEvent.ToggleFavorite)
                                }
                            )
                        }
                    }
                }
            }
        }

        // Loading overlay — keeps top bar and dots visible
        if (screenState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun PagerDotsIndicator(
    totalCount: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    maxVisible: Int = 5,
    dotSize: Dp = 6.dp,
    dotSpacing: Dp = 6.dp,
    activeColor: Color = MaterialTheme.appColors.onTopBarContainer,
    inactiveColor: Color = MaterialTheme.appColors.onTopBarContainer.copy(alpha = 0.3f),
) {
    if (totalCount <= 1) return

    // Window the dots: keep currentIndex roughly centered
    val half = maxVisible / 2
    val windowStart = (currentIndex - half).coerceIn(0, (totalCount - maxVisible).coerceAtLeast(0))
    val windowEnd = (windowStart + maxVisible).coerceAtMost(totalCount)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (i in windowStart until windowEnd) {
            val isActive = i == currentIndex
            Box(
                modifier = Modifier
                    .size(if (isActive) dotSize + 2.dp else dotSize)
                    .clip(CircleShape)
                    .background(if (isActive) activeColor else inactiveColor)
                    .animateContentSize()
            )
        }
    }
}

class ComposePagerController(
    val pagerState: PagerState,
    val controller: DetailsPagerController,
    private val scope: CoroutineScope,
) {
    fun animateToNext() {
        scope.launch {
            pagerState.animateScrollToPage(controller.currentIndex + 1)
        }
    }

    fun animateToPrevious() {
        scope.launch {
            pagerState.animateScrollToPage(controller.currentIndex - 1)
        }
    }

    fun animateToPage(index: Int) {
        scope.launch {
            pagerState.animateScrollToPage(index)
        }
    }
}

@Composable
fun rememberComposePagerController(
    controller: DetailsPagerController,
): ComposePagerController {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = controller.currentIndex,
        pageCount = { controller.totalCount },
    )
    return remember(controller) {
        ComposePagerController(
            pagerState = pagerState,
            controller = controller,
            scope = scope,
        )
    }
}




