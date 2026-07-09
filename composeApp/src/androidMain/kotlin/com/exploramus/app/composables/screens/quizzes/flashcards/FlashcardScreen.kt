package com.exploramus.app.composables.screens.quizzes.flashcards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import com.exploramus.app.composables.components.FadeInScreenContent
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.isPhone
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardState
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardStudyTarget
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

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
            currentField = screenState.studyTarget,
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
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape


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
                revealField = screenState.studyTarget,
                isRevealed = isRevealed,
                isLandscape = isLandscape,
                onRevealToggle = { isRevealed = !isRevealed },
                modifier = Modifier
                    .fillMaxHeight()
                    .then(
                        if (peekAmount > 0.dp) Modifier.padding(vertical = 16.dp)
                        else Modifier
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

        FlashcardNavBar(
            total = screenState.cards.size,
            pagerState = pagerState,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = 16.dp)
        )
    }
}

// ─── Flashcard Item ───────────────────────────────────────────────────────────

@Composable
fun FlashcardItem(
    card: FlashcardState,
    revealField: FlashcardStudyTarget,
    isRevealed: Boolean,
    isLandscape: Boolean,
    onRevealToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Container for both cards
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth(),
    ) {
        val layoutModifier = Modifier
            .widthIn(max = 520.dp)
            .fillMaxHeight()

        if (isLandscape) {
            Row(
                modifier = layoutModifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FlashcardOpenHalf(
                    card = card,
                    studyTarget = revealField,
                    shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                FlashcardHiddenHalf(
                    card = card,
                    revealField = revealField,
                    isRevealed = isRevealed,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                    onToggle = onRevealToggle,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                )
            }
        } else {
            Column(
                modifier = layoutModifier,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                FlashcardOpenHalf(
                    card = card,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth(),
                    studyTarget = revealField,
                )
                FlashcardHiddenHalf(
                    card = card,
                    revealField = revealField,
                    isRevealed = isRevealed,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                    onToggle = onRevealToggle,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .weight(5f)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

// ─── Open half (always visible) ───────────────────────────────────────────────

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

// ─── Hidden half (tap to reveal) ─────────────────────────────────────────────

@Composable
fun FlashcardHiddenHalf(
    card: FlashcardState,
    revealField: FlashcardStudyTarget,
    isRevealed: Boolean,
    shape: Shape,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
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
                rotationY = rotation
                cameraDistance = 16f * density
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (rotation <= 90f) {
                // Front side (Hidden hint)
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
                // Back side (Revealed content)
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }
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

// ─── Revealed content ─────────────────────────────────────────────────────────

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
                Spacer(modifier = Modifier.height(16.dp))
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
fun RevealPrimaryText(text: String) {
    Text(
        text = text,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
fun RevealLabeledField(label: String, value: String) {
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
fun RevealTextField(value: String) {
    Text(
        text = value,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
    )
}

@Composable
fun FlashcardFlagImage(card: FlashcardState) {
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

//@Composable
//fun FlashcardRevealedContent(
//    card: FlashcardState,
//    revealField: FlashcardStudyTarget,
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
//        modifier = Modifier.fillMaxSize(),
//    ) {
//        // Table of aligned information
//        FlashcardTable(
//            items = mutableListOf<Pair<String, String>>().apply {
//                add("Official" to card.officialName)
//                if (revealField != FlashcardStudyTarget.SECONDARY) {
//                    add("Capital" to card.capital)
//                }
//                add("Location" to card.region)
//            }
//        )
//
//        // Larger Flag Image
//        if (revealField != FlashcardStudyTarget.IMAGE) {
//            RemoteImage(
//                imageUrl = card.flagImage,
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .height(110.dp)
//                    .fillMaxWidth(0.8f),
//                shape = RoundedCornerShape(8.dp),
//                usePlaceholder = false
//            )
//        }
//    }
//}
//
//@Composable
//private fun FlashcardTable(items: List<Pair<String, String>>) {
//    Layout(
//        content = {
//            items.forEach { (label, value) ->
//                Text(
//                    text = label.uppercase(),
//                    style = MaterialTheme.typography.labelSmall,
//                    fontWeight = FontWeight.Light,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    textAlign = TextAlign.End,
//                    modifier = Modifier.layoutId("label")
//                )
//                Text(
//                    text = value,
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    textAlign = TextAlign.Start,
//                    modifier = Modifier.layoutId("value")
//                )
//            }
//        }
//    ) { measurables, constraints ->
//        val labelMeasurables = measurables.filter { it.layoutId == "label" }
//        val valueMeasurables = measurables.filter { it.layoutId == "value" }
//
//        // 1. Measure labels to find the widest one
//        val labelPlaceables = labelMeasurables.map { it.measure(constraints.copy(minWidth = 0)) }
//        val maxLabelWidth = labelPlaceables.maxOfOrNull { it.width } ?: 0
//
//        // 2. Measure values with remaining width
//        val spacing = 16.dp.roundToPx()
//        val valueConstraints = constraints.copy(
//            minWidth = 0,
//            maxWidth = (constraints.maxWidth - maxLabelWidth - spacing).coerceAtLeast(0)
//        )
//        val valuePlaceables = valueMeasurables.map { it.measure(valueConstraints) }
//
//        // 3. Calculate row heights and total dimensions
//        val rowHeights = labelPlaceables.zip(valuePlaceables).map { (l, v) -> maxOf(l.height, v.height) }
//        val maxValueWidth = valuePlaceables.maxOfOrNull { it.width } ?: 0
//
//        val totalWidth = maxLabelWidth + spacing + maxValueWidth
//        val verticalSpacing = 12.dp.roundToPx()
//        val totalHeight = rowHeights.sum() + (verticalSpacing * (items.size - 1))
//
//        // 4. Place everything
//        layout(totalWidth, totalHeight) {
//            var yPosition = 0
//            labelPlaceables.forEachIndexed { index, labelPlaceable ->
//                val valuePlaceable = valuePlaceables[index]
//                val rowHeight = rowHeights[index]
//
//                // Align label to the end of the first column
//                labelPlaceable.placeRelative(
//                    x = maxLabelWidth - labelPlaceable.width,
//                    y = yPosition + (rowHeight - labelPlaceable.height) / 2
//                )
//
//                // Align value to the start of the second column
//                valuePlaceable.placeRelative(
//                    x = maxLabelWidth + spacing,
//                    y = yPosition + (rowHeight - valuePlaceable.height) / 2
//                )
//
//                yPosition += rowHeight + verticalSpacing
//            }
//        }
//    }
//}


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

// ─── Settings dialog ──────────────────────────────────────────────────────────

@Composable
fun FlashcardSettingsDialog(
    currentField: FlashcardStudyTarget,
    onFieldSelected: (FlashcardStudyTarget) -> Unit,
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
                FlashcardStudyTarget.entries.forEach { field ->
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

private fun FlashcardStudyTarget.displayLabel(): String = when (this) {
    FlashcardStudyTarget.PRIMARY -> "Capital city"
    FlashcardStudyTarget.SECONDARY    -> "Flag"
    FlashcardStudyTarget.IMAGE  -> "Region"
}

private fun FlashcardStudyTarget.hintLabel(): String = when (this) {
    FlashcardStudyTarget.PRIMARY -> "Tap to reveal"
    FlashcardStudyTarget.SECONDARY    -> "Tap to reveal"
    FlashcardStudyTarget.IMAGE  -> "Tap to reveal"
}