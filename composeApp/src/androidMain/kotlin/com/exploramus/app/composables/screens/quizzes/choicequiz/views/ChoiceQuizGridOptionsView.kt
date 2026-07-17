package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.RemoteImage
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
    val statusIcon = when {
        !isSubmitted -> null
        isCorrect -> Icons.Default.CheckCircle
        isSelected -> Icons.Default.Cancel
        else -> null
    }
    val iconColor = getOptionContentColor(isSelected, isCorrect, isSubmitted)
    val borderWidth = getOptionBorderWidth(isSelected, isCorrect, isSubmitted, isGrid = true)

    Surface(
        onClick = onClick,
        enabled = !isSubmitted,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(borderWidth, borderColor),
        color = Color.Transparent,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            RemoteImage(
                imageUrl = option.content,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            
            if (statusIcon != null) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(24.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                )
            }
        }
    }
}
