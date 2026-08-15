package com.exploramus.app.composables.screens.quizzes.flashcards.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.composables.components.ResourceImage
import com.exploramus.app.composables.components.flagAssetUri
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.FlashcardStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardState

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
                FlashcardHintView(modifier = Modifier.fillMaxSize())
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
                RevealLabeledField(label = Strings.flashcardLabelCapital, value = card.capital)
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
                RevealLabeledField(label = Strings.flashcardLabelCapital, value = card.capital)
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
    ResourceImage(
        imageUri = flagAssetUri(card.iso2),
        contentDescription = card.itemName,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .fillMaxHeight(0.6f),
        shape = RoundedCornerShape(8.dp),
        usePlaceholder = false,
    )
}
