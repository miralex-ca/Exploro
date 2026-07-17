package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizContentType
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizOptionState

@Composable
fun ChoiceQuizGridOptionsView(
    options: List<ChoiceQuizOptionState>,
    selectedOptionId: String?,
    correctOptionId: String,
    isSubmitted: Boolean,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        FlowRow(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            options.forEach { option ->
                val isSelected = option.id == selectedOptionId
                val isCorrect = option.id == correctOptionId
                GridOptionItem(
                    option = option,
                    isSelected = isSelected,
                    isCorrect = isCorrect,
                    isSubmitted = isSubmitted,
                    onClick = { onOptionSelected(option.id) },
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1.5f)
                        .alpha(getOptionAlpha(isSelected, isCorrect, isSubmitted))
                )
            }
        }
    }
}

@Composable
private fun GridOptionItem(
    option: ChoiceQuizOptionState,
    isSelected: Boolean,
    isCorrect: Boolean,
    isSubmitted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = getOptionBorderColor(isSelected, isCorrect, isSubmitted)
    val backgroundColor = getOptionBackgroundColor(isSelected, isCorrect, isSubmitted)
    val contentColor = getOptionContentColor(isSelected, isCorrect, isSubmitted)
    val borderWidth = getOptionBorderWidth(isSelected, isCorrect, isSubmitted, isGrid = true)

    Surface(
        onClick = onClick,
        enabled = !isSubmitted,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(borderWidth, borderColor),
        color = backgroundColor,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (option.contentType) {
                ChoiceQuizContentType.IMAGE -> {
                    RemoteImage(
                        imageUrl = option.content,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().alpha(if (isSubmitted && !isCorrect && !isSelected) 0.5f else 1f)
                    )
                }
                ChoiceQuizContentType.TEXT -> {
                    Text(
                        text = option.content,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        fontWeight = if (isSelected || (isSubmitted && isCorrect)) FontWeight.Bold else FontWeight.Medium,
                        color = contentColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            if (isSubmitted && (isCorrect || isSelected)) {
                val isImage = option.contentType == ChoiceQuizContentType.IMAGE
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(
                            if (isImage) {
                                if (isCorrect) AppColorPalette.Green.icon() else AppColorPalette.Red.icon()
                            } else {
                                contentColor.copy(alpha = 0.2f)
                            }
                        )
                        .then(
                            if (isImage) {
                                Modifier.border(BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)), CircleShape)
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isCorrect) Icons.Rounded.Check else Icons.Rounded.Close,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
