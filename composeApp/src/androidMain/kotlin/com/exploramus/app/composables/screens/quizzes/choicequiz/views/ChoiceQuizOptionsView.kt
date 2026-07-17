package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizOptionState

@Composable
fun ChoiceQuizOptionsView(
    options: List<ChoiceQuizOptionState>,
    selectedOptionId: String?,
    correctOptionId: String,
    isSubmitted: Boolean,
    onOptionSelected: (String) -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = shape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            options.forEach { option ->
                val isSelected = option.id == selectedOptionId
                val isCorrect = option.id == correctOptionId
                
                val borderColor = when {
                    !isSubmitted -> {
                        if (isSelected) Color(0xCE50B0FD)
                        else MaterialTheme.appColors.cardBorder.copy(alpha = 0.1f)
                    }
                    isCorrect -> Color(0xFF2CD032)
                    isSelected -> Color(0xFFFC4032)
                    else -> MaterialTheme.appColors.cardBorder.copy(alpha = 0.3f)
                }

                val contentColor = when {
                    !isSubmitted -> {
                        if (isSelected) Color(0xFF2196F3)
                        else MaterialTheme.colorScheme.onSurface
                    }
                    isCorrect -> Color(0xFF2AB22E)
                    isSelected -> Color(0xFFFC4032)
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                }

                Surface(
                    onClick = { onOptionSelected(option.id) },
                    enabled = !isSubmitted,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(2.dp, borderColor),
                    color = MaterialTheme.appColors.quizOptionBg,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option.content,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 18.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = contentColor,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = when {
                                !isSubmitted -> Icons.Default.RadioButtonUnchecked
                                isCorrect -> Icons.Default.CheckCircle
                                isSelected -> Icons.Default.Cancel
                                else -> Icons.Default.RadioButtonUnchecked
                            },
                            contentDescription = null,
                            tint = if (isSubmitted && (isCorrect || isSelected)) contentColor 
                                   else contentColor.copy(alpha = 0.5f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
