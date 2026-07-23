package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizOptionState

@Composable
fun ChoiceQuizListOptionsView(
    options: List<ChoiceQuizOptionState>,
    selectedOptionId: String?,
    correctOptionId: String,
    isSubmitted: Boolean,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val layout = MaterialTheme.layout.quiz

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
    ) {

        val topPadding = (maxHeight * 0.1f).coerceAtLeast(5.dp)

        Column(
            modifier = modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = layout.optionsHorizontalPadding.value())
                .padding(top = layout.optionsTopPadding.value(), bottom = layout.optionsBottomPadding.value())
            ,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           // Spacer(Modifier.height(topPadding))
            options.forEach { option ->
                val isSelected = option.id == selectedOptionId
                val isCorrect = option.id == correctOptionId
                ListOptionItem(
                    option = option,
                    isSelected = isSelected,
                    isCorrect = isCorrect,
                    isSubmitted = isSubmitted,
                    onClick = { onOptionSelected(option.id) },
                    //   modifier = Modifier.alpha(getOptionAlpha(isSelected, isCorrect, isSubmitted))
                )
            }
        }

    }

}

@Composable
private fun ListOptionItem(
    option: ChoiceQuizOptionState,
    isSelected: Boolean,
    isCorrect: Boolean,
    isSubmitted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = getOptionBorderColor(isSelected, isCorrect, isSubmitted)
    val contentColor = getOptionContentColor(isSelected, isCorrect, isSubmitted)
    val backgroundColor = getOptionBackgroundColor(isSelected, isCorrect, isSubmitted)
    val borderWidth = getOptionBorderWidth(isSelected, isCorrect, isSubmitted, isGrid = false)

    Surface(
        onClick = onClick,
        enabled = !isSubmitted,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(borderWidth, borderColor),
        color = backgroundColor,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .heightIn(min = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = option.content,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 19.sp,
                fontWeight = FontWeight.Normal,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )

            if (isSubmitted && (isCorrect || isSelected)) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            ambientColor = Color.Black.copy(alpha = 0.1f),
                            spotColor = Color.Black.copy(alpha = 0.1f),
                        )
                        .background(color = backgroundColor, shape = CircleShape)
                        .background(contentColor.copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                       // imageVector = if (isCorrect) Icons.Rounded.Check else Icons.Rounded.Close,
                        painter = getChoiceQuizIconPainter(isCorrect),
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = contentColor.copy(alpha = if (isSubmitted) 0.2f else 0.5f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
