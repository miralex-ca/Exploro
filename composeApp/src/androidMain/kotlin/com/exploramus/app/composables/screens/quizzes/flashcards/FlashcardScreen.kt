package com.exploramus.app.composables.screens.quizzes.flashcards

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.FadeInScreenContent
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.isLarge
import com.exploramus.app.design.adaptive.isPhone
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardRevealField
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardState
import kotlinx.coroutines.launch

@Composable
fun FlashcardScreen(
    screenState: FlashcardScreenState,
    eventHandler: FlashcardEventHandler,
) {
    if (screenState.isLoading) {
        ScreenLoading()
    } else {
        FadeInScreenContent(durationMillis = 200) {
            FlashcardContent(
                screenState = screenState,
                onEvent = eventHandler::onEvent,
            )
        }
    }

    if (screenState.isSettingsDialogVisible) {
        FlashcardSettingsDialog(
            currentField = screenState.revealField,
            onFieldSelected = { eventHandler.onEvent(FlashcardUiEvent.OnRevealFieldChanged(it)) },
            onDismiss = { eventHandler.onEvent(FlashcardUiEvent.OnSettingsDismissed) },
        )
    }
}

// ─── Content ──────────────────────────────────────────────────────────────────

@Composable
fun FlashcardContent(
    screenState: FlashcardScreenState,
    onEvent: (FlashcardUiEvent) -> Unit,
) {
    val configuration = LocalConfiguration.current

    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val isTablet = formFactor.isLarge


    // How much of neighbouring cards to peek
    val peekAmount = when {
        formFactor.isPhone -> 20.dp   // phone portrait: no peek
       // isLandscape && !isTablet -> 48.dp   // phone landscape: moderate peek
        else -> 30.dp                        // tablet any orientation: wide peek
    }

    val pagerState = rememberPagerState(
        initialPage = screenState.currentIndex,
        pageCount = { screenState.cards.size },
    )

    LaunchedEffect(pagerState.currentPage) {
        onEvent(FlashcardUiEvent.OnPageChanged(pagerState.currentPage))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = peekAmount),
            pageSpacing = 12.dp,
            beyondViewportPageCount = 2,
            modifier = Modifier.weight(1f),
        ) { page ->
            val card = screenState.cards[page]
            var isRevealed by remember(page) { mutableStateOf(false) }

            FlashcardItem(
                card = card,
                revealField = screenState.revealField,
                isRevealed = isRevealed,
                isLandscape = isLandscape,
                onRevealToggle = { isRevealed = !isRevealed },
                modifier = Modifier
                    .fillMaxHeight()
                    .then(
                        if (peekAmount > 0.dp) Modifier.padding(vertical = 16.dp)
                        else Modifier
                    ),
            )
        }

        FlashcardNavBar(
            total = screenState.cards.size,
            pagerState = pagerState,
        )
    }
}

// ─── Flashcard Item ───────────────────────────────────────────────────────────

@Composable
fun FlashcardItem(
    card: FlashcardState,
    revealField: FlashcardRevealField,
    isRevealed: Boolean,
    isLandscape: Boolean,
    onRevealToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Cap width on tablets / landscape so card never stretches absurdly wide
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth(),
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .widthIn(max = 520.dp)
                .fillMaxHeight(),
        ) {
            if (isLandscape) {
                // ── Landscape: side-by-side ──
                Row(modifier = Modifier.fillMaxSize()) {
                    FlashcardOpenHalf(
                        card = card,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                    VerticalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                        modifier = Modifier.fillMaxHeight(),
                    )
                    FlashcardHiddenHalf(
                        card = card,
                        revealField = revealField,
                        isRevealed = isRevealed,
                        onToggle = onRevealToggle,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                }
            } else {
                // ── Portrait: stacked ──
                Column(modifier = Modifier.fillMaxSize()) {
                    FlashcardOpenHalf(
                        card = card,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    )
                    FlashcardHiddenHalf(
                        card = card,
                        revealField = revealField,
                        isRevealed = isRevealed,
                        onToggle = onRevealToggle,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

// ─── Open half (always visible) ───────────────────────────────────────────────

@Composable
fun FlashcardOpenHalf(
    card: FlashcardState,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(24.dp),
    ) {
        Text(
            text = card.countryName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

// ─── Hidden half (tap to reveal) ─────────────────────────────────────────────

@Composable
fun FlashcardHiddenHalf(
    card: FlashcardState,
    revealField: FlashcardRevealField,
    isRevealed: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val revealColor = MaterialTheme.colorScheme.primary

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onToggle,
            )
            .padding(24.dp),
    ) {
        // Crossfade between hidden hint and revealed content
        Crossfade(
            targetState = isRevealed,
            animationSpec = tween(durationMillis = 250),
            label = "reveal_crossfade",
        ) { revealed ->
            if (!revealed) {
                // Hidden state — subtle prompt
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                        modifier = Modifier.size(28.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = revealField.hintLabel(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                // Revealed state
                FlashcardRevealedContent(
                    card = card,
                    revealField = revealField,
                    accentColor = revealColor,
                )
            }
        }
    }
}

// ─── Revealed content ─────────────────────────────────────────────────────────

@Composable
fun FlashcardRevealedContent(
    card: FlashcardState,
    revealField: FlashcardRevealField,
    accentColor: Color,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        when (revealField) {
            FlashcardRevealField.CAPITAL -> {
                Text(
                    text = card.capital,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = accentColor,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = card.flagEmoji,
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = card.region,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                    textAlign = TextAlign.Center,
                )
            }
            FlashcardRevealField.FLAG -> {
                Text(
                    text = card.flagEmoji,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = card.capital,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = card.region,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                    textAlign = TextAlign.Center,
                )
            }
            FlashcardRevealField.REGION -> {
                Text(
                    text = card.region,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = accentColor,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = card.flagEmoji,
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = card.capital,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}


@Composable
fun FlashcardNavBar(
    total: Int,
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage  // observed directly — recomposes on every page change

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
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

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "${currentPage + 1} / $total",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.width(16.dp))

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

// ─── Settings dialog ──────────────────────────────────────────────────────────

@Composable
fun FlashcardSettingsDialog(
    currentField: FlashcardRevealField,
    onFieldSelected: (FlashcardRevealField) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Reveal on tap",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Column {
                Text(
                    text = "Choose what to show in the hidden half.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlashcardRevealField.entries.forEach { field ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(),
                                onClick = {
                                    onFieldSelected(field)
                                    onDismiss()
                                },
                            )
                            .padding(vertical = 10.dp),
                    ) {
                        RadioButton(
                            selected = currentField == field,
                            onClick = {
                                onFieldSelected(field)
                                onDismiss()
                            },
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = field.displayLabel(),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        },
    )
}

// ─── FlashcardRevealField helpers ─────────────────────────────────────────────

private fun FlashcardRevealField.displayLabel(): String = when (this) {
    FlashcardRevealField.CAPITAL -> "Capital city"
    FlashcardRevealField.FLAG    -> "Flag"
    FlashcardRevealField.REGION  -> "Region"
}

private fun FlashcardRevealField.hintLabel(): String = when (this) {
    FlashcardRevealField.CAPITAL -> "Tap to reveal capital"
    FlashcardRevealField.FLAG    -> "Tap to reveal flag"
    FlashcardRevealField.REGION  -> "Tap to reveal region"
}