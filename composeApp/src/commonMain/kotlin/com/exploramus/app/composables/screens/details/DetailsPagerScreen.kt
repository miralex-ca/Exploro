package com.exploramus.app.composables.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.FadeInScreenContent
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.navigation.ui.topbars.DetailsPagerTopBar
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.AppTypography
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.details.CountryDetailsState
import com.exploramus.shared.viewmodel.screens.details.detailpager.DetailsPagerScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.seconds

@Composable
fun DetailsPagerScreen(
    screenState: DetailsPagerScreenState,
    eventHandler: DetailsPagerEventHandler
) {

    FadeInScreenContent {
        when {
            screenState.isLoading -> {
                ScreenLoading()
            }

            screenState.detailsList.isEmpty() -> {
                EmptyStateView(EmptyState.NotFound)
            }

            else -> {
                key(screenState.resetKey) {
                    DetailsPagerScreenContent(
                        detailsList = screenState.detailsList,
                        initialIndex = screenState.initialIndex,
                        onEvent = eventHandler::onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsPagerScreenContent(
    detailsList: List<CountryDetailsState>,
    initialIndex: Int,
    onEvent: (DetailsPagerUiEvent) -> Unit,
) {

    val itemsCount = detailsList.size

    val detailsPager = rememberDetailsPagerState(
        initialPage = initialIndex,
        pageCount = itemsCount,
    )

    val currentDetails = detailsList.getOrNull(detailsPager.pagerState.currentPage)

    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            DetailsPagerTopBar(
                title = currentDetails?.name ?: "",
                mapsUrl = currentDetails?.mapsUrl,
                wikiUrl = currentDetails?.wikiUrl,
                onBackClick = { onEvent(DetailsPagerUiEvent.OnBackClicked) },
                onPreviousClick = {
                    scope.launch {
                        detailsPager.previous()
                    }
                },
                onNextClick = {
                    scope.launch {
                        detailsPager.next()
                    }
                },
                hasPrevious = detailsPager.hasPrevious,
                hasNext = detailsPager.hasNext,
            )

            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = detailsPager.pagerState,
                    beyondViewportPageCount = 1,
                    modifier = Modifier.fillMaxSize(),
                ) { page ->
                    val details = detailsList.getOrNull(page)

                    if (details != null) {
                        DetailsPagerPage(
                            details = details,
                            pagerState = detailsPager.pagerState,
                            page = page,
                            onEvent = onEvent
                        )
                    }
                }

                DetailsPagerIndicator(
                    visible = detailsPager.indicatorVisible,
                    currentPage = detailsPager.pagerState.currentPage,
                    pageCount = itemsCount,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Stable
class DetailsPagerState(
    internal val pagerState: PagerState,
    val indicatorVisible: Boolean,
) {
    val currentPage get() = pagerState.currentPage

    val pageCount get() = pagerState.pageCount

    val hasPrevious get() = currentPage > 0

    val hasNext get() = currentPage < pageCount - 1

    suspend fun previous() {
        if (hasPrevious) {
            pagerState.animateScrollToPage(currentPage - 1)
        }
    }

    suspend fun next() {
        if (hasNext) {
            pagerState.animateScrollToPage(currentPage + 1)
        }
    }
}

@Composable
fun rememberDetailsPagerState(
    initialPage: Int,
    pageCount: Int,
): DetailsPagerState {

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { pageCount },
    )

    var indicatorVisible by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(
        pagerState.currentPage,
        pagerState.isScrollInProgress,
    ) {
        if (pagerState.isScrollInProgress) {
            indicatorVisible = true
        } else {
            delay(2.seconds)
            indicatorVisible = false
        }
    }

    return remember(pagerState, indicatorVisible) {
        DetailsPagerState(
            pagerState,
            indicatorVisible,
        )
    }
}

@Composable
private fun DetailsPagerPage(
    details: CountryDetailsState,
    pagerState: PagerState,
    page: Int,
    onEvent: (DetailsPagerUiEvent) -> Unit
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val layout = MaterialTheme.layout.details

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
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                    val scale = lerp(
                        start = 0.95f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleX = scale
                    scaleY = scale
                }
        ) {
            if (isLandscape) {
                LargeDetailsSections(
                    details = details,
                    onFavoriteClick = {
                        onEvent(DetailsPagerUiEvent.ToggleFavorite(details.id))
                    }
                )
            } else {
                DetailsSections(
                    details = details,
                    onFavoriteClick = {
                        onEvent(DetailsPagerUiEvent.ToggleFavorite(details.id))
                    }
                )
            }
        }
    }
}

@Composable
private fun DetailsPagerIndicator(
    modifier: Modifier = Modifier,
    visible: Boolean,
    currentPage: Int,
    pageCount: Int,
) {
    Row(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(250)),
            exit = fadeOut(animationSpec = tween(500)),
        ) {

            val shadowElevation by animateFloatAsState(
                targetValue = if (visible) 2f else 0f,
                animationSpec = tween(100, delayMillis = 350),
                label = "shadow"
            )

            Surface(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(bottom = 12.dp)
                    .padding(horizontal = 12.dp)
                    .shadow(
                        elevation = shadowElevation.dp,
                        shape = RoundedCornerShape(50),
                        clip = false,
                        spotColor = Color.Black.copy(alpha = 0.5f)
                    ),
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                tonalElevation = 3.dp,
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.appColors.pagerIndicatorBorder
                ),
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${currentPage + 1}",
                        style = AppTypography.pagerIndicatorText,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .defaultMinSize(minWidth = 24.dp)
                            .padding(end = 3.dp)
                    )
                    Text(
                        text = " / ",
                        style = AppTypography.pagerIndicatorText,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "${pageCount}",
                        style = AppTypography.pagerIndicatorText,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .defaultMinSize(minWidth = 24.dp)
                            .padding(end = 2.dp)
                    )
                }
            }
        }
    }
}






