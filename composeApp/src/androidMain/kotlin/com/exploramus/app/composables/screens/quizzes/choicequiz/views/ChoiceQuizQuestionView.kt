package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.composables.screens.quizzes.utils.toPrompt
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizContentType
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizQuestionState

@Composable
fun ChoiceQuizQuestionView(
    question: ChoiceQuizQuestionState,
    shape: Shape,
    modifier: Modifier = Modifier,
) {
    val layout = MaterialTheme.layout.quiz
    Card(
        shape = shape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder,
        ),
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            val prompt = question.toPrompt()
            if (prompt != null) {
                Text(
                    text = prompt,
                    fontSize = layout.questionTextSize.value(),
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                )
            }

            Box(
                contentAlignment = when (question.contentType) {
                    ChoiceQuizContentType.TEXT -> BiasAlignment(
                        horizontalBias = 0f,
                        verticalBias = layout.questionTextVerticalAlign
                    )
                    ChoiceQuizContentType.IMAGE -> Alignment.Center
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                when (question.contentType) {
                    ChoiceQuizContentType.TEXT -> {
                        Text(
                            text = question.content,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    ChoiceQuizContentType.IMAGE -> {
                        RemoteImage(
                            imageUrl = question.content,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize(0.9f),
                            shape = RoundedCornerShape(8.dp),
                            usePlaceholder = false,
                        )
                    }
                }
            }
        }
    }
}
