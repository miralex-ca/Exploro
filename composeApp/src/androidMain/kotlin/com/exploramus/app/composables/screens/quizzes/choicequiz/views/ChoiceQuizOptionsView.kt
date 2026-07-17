package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizContentType
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
    val isImageQuiz = options.any { it.contentType == ChoiceQuizContentType.IMAGE }

    Card(
        shape = shape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
        modifier = modifier
    ) {
        if (isImageQuiz) {
            ChoiceQuizGridOptionsView(
                options = options,
                selectedOptionId = selectedOptionId,
                correctOptionId = correctOptionId,
                isSubmitted = isSubmitted,
                onOptionSelected = onOptionSelected
            )
        } else {
            ChoiceQuizListOptionsView(
                options = options,
                selectedOptionId = selectedOptionId,
                correctOptionId = correctOptionId,
                isSubmitted = isSubmitted,
                onOptionSelected = onOptionSelected
            )
        }
    }
}
