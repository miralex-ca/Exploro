package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.RemoveCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.composables.components.dialogs.NoPaddingAlertDialog
import com.exploramus.app.composables.screens.quizzes.utils.QuizResultGrade
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.app.resources.Strings
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizAnswerStatus
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizContentType
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizItemState
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
    type: QuizResultMetricType,
    results: ChoiceQuizResultsState,
    grade: QuizResultGrade,
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
                TotalMetricContent(results = results)
            }
            QuizResultMetricType.SCORE -> {
                ScoreDialogContent(
                    scorePercentage = scorePercentage,
                    results = results,
                    grade = grade
                )
            }
            QuizResultMetricType.CORRECT -> {
                MetricListDialogContent(
                    type = QuizResultMetricType.CORRECT,
                    count = results.correctCount,
                    label = Strings.quizResultMetricCorrect,
                    items = results.items.filter { it.status == ChoiceQuizAnswerStatus.CORRECT }
                )
            }
            QuizResultMetricType.ERRORS -> {
                MetricListDialogContent(
                    type = QuizResultMetricType.ERRORS,
                    count = results.incorrectCount,
                    label = Strings.quizResultMetricIncorrect,
                    items = results.items.filter { it.status == ChoiceQuizAnswerStatus.INCORRECT }
                )
            }
        }
    }
}

@Composable
fun TotalMetricContent(
    results: ChoiceQuizResultsState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            MetricInfoRow(label = Strings.quizResultMetricTotal, value = results.totalCount.toString())
            MetricInfoRow(
                label = Strings.quizResultMetricCorrect,
                value = results.correctCount.toString(),
                color = AppColorPalette.Green.text()
            )
            MetricInfoRow(
                label = Strings.quizResultMetricIncorrect,
                value = results.incorrectCount.toString(),
                color = AppColorPalette.Red.text()
            )
            MetricInfoRow(
                label = Strings.quizResultMetricSkipped,
                value = results.skipped.toString(),
                labelFontWeight = FontWeight.Normal,
                valueFontWeight = FontWeight.Normal,
            )
        }

        val skippedItems = results.items.filter { it.status == ChoiceQuizAnswerStatus.NOT_ANSWERED }
        if (skippedItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(skippedItems) { item ->
                    QuestionItem(
                        item = item,
                        statusIcon = Icons.Rounded.RemoveCircleOutline,
                        statusIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    ) {
                        val correctOption = item.options.find { it.id == item.correctOptionId }
                        if (correctOption?.contentType == ChoiceQuizContentType.IMAGE) {
                            AnswerImageItem(
                                imageUrl = correctOption.content
                            )
                        } else {
                            AnswerRow(
                                text = correctOption?.content ?: "",
                                textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MetricListDialogContent(
    type: QuizResultMetricType,
    count: Int,
    label: String,
    items: List<ChoiceQuizItemState>,
    modifier: Modifier = Modifier
) {
    val statusIcon = when (type) {
        QuizResultMetricType.CORRECT -> Icons.Rounded.Check
        QuizResultMetricType.ERRORS -> Icons.Rounded.Close
        else -> null
    }
    val statusIconColor = when (type) {
        QuizResultMetricType.CORRECT -> AppColorPalette.Green.text()
        QuizResultMetricType.ERRORS -> AppColorPalette.Red.text()
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MetricInfoRow(
            label = label,
            value = count.toString(),
            color = statusIconColor
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(items) { item ->
                val correctOption = item.options.find { it.id == item.correctOptionId }

                QuestionItem(
                    item = item,
                    statusIcon = statusIcon,
                    statusIconColor = statusIconColor
                ) {
                    if (correctOption?.contentType == ChoiceQuizContentType.IMAGE) {
                        AnswerImageItem(
                            imageUrl = correctOption.content
                        )
                    } else {
                        AnswerRow(
                            text = correctOption?.content ?: "",
                            textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestionItem(
    item: ChoiceQuizItemState,
    modifier: Modifier = Modifier,
    statusIcon: ImageVector? = null,
    statusIconColor: Color = MaterialTheme.colorScheme.onSurface,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (statusIcon != null) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = null,
                    tint = statusIconColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            if (item.question.contentType == ChoiceQuizContentType.IMAGE) {
                RemoteImage(
                    imageUrl = item.question.content,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(30.dp),
                    shape = RoundedCornerShape(4.dp),
                    usePlaceholder = false
                )
            } else {
                Text(
                    text = item.question.content,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Box(modifier = Modifier.padding(start = if (statusIcon != null) 24.dp else 0.dp)) {
            Column {
                content()
            }
        }
    }
}

@Composable
private fun AnswerRow(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(textDecoration = textDecoration),
        color = textColor,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun AnswerImageItem(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
    ) {
        RemoteImage(
            imageUrl = imageUrl,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxHeight(),
            shape = RoundedCornerShape(4.dp),
            usePlaceholder = false
        )
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
            text = Strings.quizResultScoreDescription,
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
                label = Strings.quizResultMetricCorrect,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(
                modifier = Modifier
                    .height(36.dp)
                    .padding(horizontal = 8.dp)
            )
            ScoreStat(
                value = results.totalCount.toString(),
                label = Strings.quizResultMetricTotal,
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
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    labelFontWeight: FontWeight = FontWeight.Medium,
    valueFontWeight: FontWeight = FontWeight.Bold,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textColor = if (color == MaterialTheme.colorScheme.onSurface) 
            MaterialTheme.colorScheme.onSurface
        else color

        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = labelFontWeight,
            color = textColor
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = valueFontWeight,
            color = color
        )
    }
}

enum class QuizResultMetricType {
    TOTAL, SCORE, CORRECT, ERRORS
}
