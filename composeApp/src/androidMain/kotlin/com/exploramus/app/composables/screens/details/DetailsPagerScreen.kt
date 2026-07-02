package com.exploramus.app.composables.screens.details


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
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
import com.exploramus.shared.viewmodel.screens.details.detailpager.DetailsPagerScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.milliseconds


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
                        screenState = screenState,
                        onEvent = eventHandler::onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsPagerScreenContent(
    screenState: DetailsPagerScreenState,
    onEvent: (DetailsPagerUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val layout = MaterialTheme.layout.details

     val pagerState = rememberPagerState(
        initialPage = screenState.initialIndex,
        pageCount = { screenState.detailsList.size },
    )

    val scope = rememberCoroutineScope()

    val currentPage = pagerState.currentPage
    val currentDetails = screenState.detailsList.getOrNull(currentPage)
    var lastReportedPage by remember { mutableIntStateOf(pagerState.currentPage) }

    LaunchedEffect(pagerState.settledPage) {
        val settled = pagerState.settledPage
        if (settled != lastReportedPage) {
            lastReportedPage = settled
           // onEvent(DetailsPagerUiEvent.OnPageChanged(settled))
        }
    }

    var indicatorVisible by remember { mutableStateOf(true) }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress) {
            indicatorVisible = true
        } else {
            delay(2000.milliseconds)
            indicatorVisible = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            DetailsPagerTopBar(
                title = currentDetails?.name ?: "",
                mapsUrl = currentDetails?.mapsUrl,
                wikiUrl = currentDetails?.wikiUrl,
                onBackClick = { onEvent(DetailsPagerUiEvent.OnBackClicked) },
                onPreviousClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(currentPage - 1)
                    }
                },
                onNextClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(currentPage + 1)
                    }
                },
                hasPrevious = currentPage > 0,
                hasNext = currentPage < screenState.detailsList.size - 1,
            )

            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                    beyondViewportPageCount = 1,
                    modifier = Modifier.fillMaxSize(),
                ) { page ->

                    val details = screenState.detailsList.getOrNull(page)

                    if (details != null) {
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
                                        val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

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
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                ) {
                    AnimatedVisibility(
                        visible = indicatorVisible,
                        enter = fadeIn(animationSpec = tween(250)),
                        exit = fadeOut(animationSpec = tween(500)),
                        ) {

                        val shadowElevation by animateFloatAsState(
                            targetValue = if (indicatorVisible) 2f else 0f,
                            animationSpec = tween(100, delayMillis = 350 ),
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
                                    text = "${pagerState.currentPage + 1}",
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
                                    text = "${screenState.detailsList.size}",
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
        }
    }
}




