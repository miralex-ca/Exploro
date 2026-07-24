package com.exploramus.app.composables.components.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.composables.screens.quizzes.utils.QuizResultGrade
import com.exploramus.app.resources.Strings
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizResultsState

@Composable
fun MetricInfoDialog(
    isVisible: Boolean,
    title: String,
    onDismiss: () -> Unit,
    message: String? = null,
    content: @Composable (() -> Unit)? = null,
    dismissText: String = Strings.commonClose,
) {
    if (!isVisible) return

    NoPaddingAlertDialog(
        titleText = title,
        content = {
            if (content != null) {
                content()
            } else if (message != null) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 0.dp, bottom = 12.dp)
                )
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = dismissText,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    )
}

@Composable
fun ChoiceQuizMetricContent(
    type: QuizResultMetricType?,
    results: ChoiceQuizResultsState,
    grade: QuizResultGrade,
    label: String,
    modifier: Modifier = Modifier
) {
    val scorePercentage = (results.score * 100).toInt()

    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(top = 0.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (type) {
            QuizResultMetricType.TOTAL -> {
                MetricInfoRow(label = "Total questions", value = results.totalCount.toString())
                MetricInfoRow(label = "Answered questions", value = (results.totalCount - results.skipped).toString())
                MetricInfoRow(label = "Skipped questions", value = results.skipped.toString())
            }
            QuizResultMetricType.SCORE -> {
                ScoreDialogContent(
                    scorePercentage = scorePercentage,
                    results = results,
                    grade = grade
                )
            }
            else -> {
                Text(
                    text = "Information about $label will be added here.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ScoreDialogContent(
    scorePercentage: Int,
    results: ChoiceQuizResultsState,
    grade: QuizResultGrade,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { scorePercentage / 100f },
                modifier = Modifier.size(120.dp),
                color = grade.colorSet.text(),
                trackColor = grade.colorSet.text().copy(alpha = 0.15f),
                strokeWidth = 8.dp,
            )
            Text(
                text = "$scorePercentage%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = grade.colorSet.text()
            )
        }

        Text(
            text = "Score is calculated as the percentage of correct answers relative to the total number of questions.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            ScoreStat(
                value = results.correctCount.toString(),
                label = "Correct answers",
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(
                modifier = Modifier
                    .height(36.dp)
                    .padding(horizontal = 8.dp)
            )
            ScoreStat(
                value = results.totalCount.toString(),
                label = "Total questions",
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ScoreStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun MetricInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

enum class QuizResultMetricType {
    TOTAL, SCORE, CORRECT, ERRORS
}

 
