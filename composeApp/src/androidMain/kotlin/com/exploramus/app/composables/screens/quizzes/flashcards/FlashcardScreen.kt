package com.exploramus.app.composables.screens.quizzes.flashcards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.exploramus.app.R
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardSettingsDialog
import com.exploramus.app.composables.screens.quizzes.flashcards.views.FlashcardsTopBar
import com.exploramus.app.design.adaptive.HeightType
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.core.models.FlashcardStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun FlashcardScreen(
    screenState: FlashcardScreenState,
    eventHandler: FlashcardEventHandler,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (screenState.isLoading) {
            FlashcardsTopBar(
                title = screenState.screenTitle,
                onBackClick = { eventHandler.onEvent(FlashcardUiEvent.OnBackClicked) },
                onEvent = eventHandler::onEvent,
            )
            ScreenLoading()
        } else {
            key(screenState.revision) {
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { screenState.cards.size },
                )
                FlashcardsTopBar(
                    title = screenState.screenTitle,
                    onBackClick = { eventHandler.onEvent(FlashcardUiEvent.OnBackClicked) },
                    onEvent = eventHandler::onEvent,
                    pagerState = pagerState
                )
                FlashcardContent(
                    screenState = screenState,
                    onEvent = eventHandler::onEvent,
                    pagerState = pagerState
                )
            }
        }
    }

    if (screenState.isSettingsDialogVisible) {
        FlashcardSettingsDialog(
            currentConfig = screenState.config,
            onConfigChanged = { eventHandler.onEvent(FlashcardUiEvent.OnConfigChanged(it)) },
            onDismiss = { eventHandler.onEvent(FlashcardUiEvent.OnSettingsDismissed) },
        )
    }
}

@Composable
fun FlashcardContent(
    screenState: FlashcardScreenState,
    onEvent: (FlashcardUiEvent) -> Unit,
    pagerState: PagerState,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape

    val layout = MaterialTheme.layout.flashcard

    LaunchedEffect(pagerState.currentPage) {
        onEvent(FlashcardUiEvent.OnPageChanged(pagerState.currentPage))
    }

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
            val card = screenState.cards[page]
            var isRevealed by retain(page) { mutableStateOf(screenState.config.revealEnabled) }

            FlashcardItem(
                card = card,
                studyTarget = screenState.config.studyTarget,
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
                total = screenState.cards.size,
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
                horizontalArrangement = Arrangement.spacedBy(2.dp)
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
                verticalArrangement = Arrangement.spacedBy(2.dp)
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
fun FlashcardOpenHalf(
    card: FlashcardState,
    studyTarget: FlashcardStudyTarget,
    shape: Shape,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = shape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder,
        ),
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            when (studyTarget) {
                FlashcardStudyTarget.PRIMARY -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                    ) {
                        Text(
                            text = card.itemName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
                FlashcardStudyTarget.SECONDARY -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                    ) {
                        Text(
                            text = card.capital,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
                FlashcardStudyTarget.IMAGE -> {
                    RemoteImage(
                        imageUrl = card.flagImage,
                        contentDescription = card.itemName,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(0.75f)
                            .fillMaxHeight(0.6f),
                        shape = RoundedCornerShape(8.dp),
                        usePlaceholder = false,
                    )
                }
            }
        }
    }
}


@Composable
fun FlashcardHiddenHalf(
    modifier: Modifier = Modifier,
    card: FlashcardState,
    revealField: FlashcardStudyTarget,
    isRevealed: Boolean,
    isLandscape: Boolean = false,
    shape: Shape,
    onToggle: () -> Unit,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isRevealed) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "flip_rotation",
    )

    Card(
        shape = shape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
        onClick = onToggle,
        modifier = modifier
            .graphicsLayer {
                if (isLandscape) {
                    rotationX = rotation
                } else {
                    rotationY = rotation
                }
                cameraDistance = 16f * density
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (rotation <= 90f) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(140.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                    shape = CircleShape,
                                )
                        )
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                    shape = CircleShape,
                                )
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.cards),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                            modifier = Modifier.size(70.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = revealField.hintLabel(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            if (isLandscape) rotationX = 180f else rotationY = 180f
                        }
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FlashcardRevealedContent(
                        card = card,
                        revealField = revealField,
                    )
                }
            }
        }
    }
}


@Composable
fun FlashcardRevealedContent(
    card: FlashcardState,
    revealField: FlashcardStudyTarget,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        when (revealField) {
            FlashcardStudyTarget.PRIMARY -> {
                RevealPrimaryText(text = card.officialName)
                Spacer(modifier = Modifier.height(16.dp))
                RevealLabeledField(label = "Capital", value = card.capital)
                Spacer(modifier = Modifier.height(12.dp))
                RevealTextField(value = card.region)
                Spacer(modifier = Modifier.height(16.dp))
                FlashcardFlagImage(card = card)
            }
            FlashcardStudyTarget.SECONDARY -> {
                RevealPrimaryText(text = card.officialName)
                Spacer(modifier = Modifier.height(12.dp))
                RevealTextField(value = card.region)
                Spacer(modifier = Modifier.height(30.dp))
                FlashcardFlagImage(card = card)
            }
            FlashcardStudyTarget.IMAGE -> {
                RevealPrimaryText(text = card.officialName)
                Spacer(modifier = Modifier.height(16.dp))
                RevealLabeledField(label = "Capital", value = card.capital)
                Spacer(modifier = Modifier.height(16.dp))
                RevealTextField(value = card.region)
            }
        }
    }
}

@Composable
private fun RevealPrimaryText(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
private fun RevealLabeledField(label: String, value: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Normal)) {
                append("$label: ")
            }
            withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                append(value)
            }
        },
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.95f),
    )
}

@Composable
private fun RevealTextField(value: String) {
    Text(
        text = value,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
    )
}

@Composable
private fun FlashcardFlagImage(card: FlashcardState) {
    RemoteImage(
        imageUrl = card.flagImage,
        contentDescription = card.itemName,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .fillMaxHeight(0.6f),
        shape = RoundedCornerShape(8.dp),
        usePlaceholder = false,
    )
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
                    contentDescription = "Previous",
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
                    contentDescription = "Next",
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

private fun FlashcardStudyTarget.hintLabel(): String = when (this) {
    FlashcardStudyTarget.PRIMARY -> "Tap to reveal"
    FlashcardStudyTarget.SECONDARY    -> "Tap to reveal"
    FlashcardStudyTarget.IMAGE  -> "Tap to reveal"
}
