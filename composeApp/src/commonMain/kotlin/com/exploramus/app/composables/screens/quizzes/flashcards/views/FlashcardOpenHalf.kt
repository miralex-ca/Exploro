package com.exploramus.app.composables.screens.quizzes.flashcards.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.ResourceImage
import com.exploramus.app.composables.components.flagAssetUri
import com.exploramus.app.design.theme.appColors
import com.exploramus.core.models.FlashcardStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardState

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
                    ResourceImage(
                        imageUri = flagAssetUri(card.iso2),
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
