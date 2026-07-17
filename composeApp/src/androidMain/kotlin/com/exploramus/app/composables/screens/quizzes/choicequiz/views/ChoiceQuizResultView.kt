package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizResultsState
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.getEvaluation

@Composable
fun ChoiceQuizResultView(
    results: ChoiceQuizResultsState,
    onRestartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val evaluation = results.getEvaluation()
    val layout = MaterialTheme.layout.flashcard
    val isLandscape = LocalFormFactor.current.isLandscape

    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = layout.maxHeight.value())
            .padding(top = layout.topPadding.value()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = if (isLandscape) layout.landScapeCardMaxWidth.value() else layout.cardMaxWidth.value())
                .weight(1f)
                .padding(horizontal = layout.cardHorizontalPadding.value()),
            contentAlignment = Alignment.Center
        ) {
            if (isLandscape) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    ResultEvaluationCard(
                        evaluation = evaluation,
                        shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
                        modifier = Modifier.weight(0.65f).fillMaxHeight()
                    )
                    ResultStatsCard(
                        results = results,
                        evaluationColor = Color(evaluation.color),
                        onRestartClick = onRestartClick,
                        shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    ResultEvaluationCard(
                        evaluation = evaluation,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        modifier = Modifier.weight(0.4f).fillMaxWidth()
                    )
                    ResultStatsCard(
                        results = results,
                        evaluationColor = Color(evaluation.color),
                        onRestartClick = onRestartClick,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                        modifier = Modifier.weight(1f).fillMaxWidth().padding(bottom = layout.cardBottomPadding.value())
                    )
                }
            }
        }

        // Navigation Bar Placeholder
        Spacer(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = layout.bottomBarPadding.value())
                .height(72.dp)
        )
    }
}

@Composable
private fun ResultEvaluationCard(
    evaluation: com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizEvaluation,
    shape: androidx.compose.ui.graphics.Shape,
    modifier: Modifier = Modifier
) {
    Card(
        shape = shape,
        border = BorderStroke(1.dp, MaterialTheme.appColors.cardBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = evaluation.emoji, fontSize = 64.sp)
            Text(
                text = evaluation.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(evaluation.color),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ResultStatsCard(
    results: ChoiceQuizResultsState,
    evaluationColor: Color,
    onRestartClick: () -> Unit,
    shape: androidx.compose.ui.graphics.Shape,
    modifier: Modifier = Modifier
) {
    Card(
        shape = shape,
        border = BorderStroke(1.dp, MaterialTheme.appColors.cardBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Summary",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Correct answers: ${results.correctCount} / ${results.correctCount + results.incorrectCount}",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
                Text(
                    text = "Result: ${(results.score * 100).toInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = evaluationColor
                )
            }

            Button(
                onClick = onRestartClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = Strings.flashcardRestart, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
