package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.composables.screens.quizzes.utils.QuizResultGrade
import com.exploramus.app.design.adaptive.*
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.app.design.theme.AppColorSet
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizResultsState

@Composable
fun ChoiceQuizResultView(
    results: ChoiceQuizResultsState,
    onRestartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scorePercentage = (results.score * 100).toInt()
    val grade = getQuizResultGrade(scorePercentage)

    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val isCompactLandscape = formFactor.isCompactHeight && formFactor.isLandscape

    val layout = MaterialTheme.layout.quiz

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(max = layout.resultMaxHeight.value())
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = if (isCompactLandscape) 0.dp else (layout.cardBottomPadding.value() + 82.dp)),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        ResultEvaluationCard(
                            grade = grade,
                            shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
                            modifier = Modifier
                                .weight(0.65f)
                            //.fillMaxHeight()
                        )
                        ResultStatsCard(
                            results = results,
                            grade = grade,
                            onRestartClick = onRestartClick,
                            shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                            modifier = Modifier
                                .weight(1f)
                             .fillMaxHeight()

                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {

                        ResultEvaluationCard(
                            grade = grade,
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                            modifier = Modifier
                                .weight(0.4f)
                                .fillMaxWidth()
                        )
                        ResultStatsCard(
                            results = results,
                            grade = grade,
                            onRestartClick = onRestartClick,
                            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(bottom = layout.cardBottomPadding.value() + 82.dp)
                        )
                    } 
                }
            } 

            Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
            )
        }
    }
}

@Composable
private fun ResultEvaluationCard(
    grade: QuizResultGrade,
    shape: androidx.compose.ui.graphics.Shape,
    modifier: Modifier = Modifier
) {

    val contentColor = grade.colorSet.text()
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
            Text(
                text = grade.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Icon(
                imageVector = grade.icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(72.dp),
            )
        }
    }
}

@Composable
private fun ResultStatsCard(
    results: ChoiceQuizResultsState,
    grade: QuizResultGrade,
    onRestartClick: () -> Unit,
    shape: androidx.compose.ui.graphics.Shape,
    modifier: Modifier = Modifier
) {
    val totalCount = results.totalCount
    val scorePercentage = (results.score * 100).toInt()

    val layout = MaterialTheme.layout.quiz

    val metrics = listOf(
        QuizResultMetric(
            type = QuizResultMetricType.TOTAL,
            label = Strings.quizResultMetricTotal,
            value = totalCount.toString(),
            icon = Icons.Rounded.FormatListNumbered,
            colorSet = AppColorPalette.BlueGrey
        ),
        QuizResultMetric(
            type = QuizResultMetricType.SCORE,
            label = Strings.quizResultMetricScore,
            value = "$scorePercentage%",
            icon = Icons.Rounded.BarChart,
            colorSet = grade.colorSet
        ),
        QuizResultMetric(
            type = QuizResultMetricType.CORRECT,
            label = Strings.quizResultMetricCorrect,
            value = results.correctCount.toString(),
            icon = Icons.Rounded.Check,
            colorSet = AppColorPalette.Green
        ),
        QuizResultMetric(
            type = QuizResultMetricType.ERRORS,
            label = Strings.quizResultMetricIncorrect,
            value = results.incorrectCount.toString(),
            icon = Icons.Rounded.Close,
            colorSet = AppColorPalette.Red
        )
    )

    var selectedMetricType by remember { mutableStateOf<QuizResultMetricType?>(null) }
    val selectedMetric = metrics.find { it.type == selectedMetricType }

    MetricInfoDialog(
        isVisible = selectedMetric != null,
        title = selectedMetric?.label ?: "",
        onDismiss = { selectedMetricType = null },
        content = {
            selectedMetricType?.let { type ->
                ChoiceQuizMetricContent(
                    type = type,
                    results = results,
                    grade = grade
                )
            }
        }
    )

    Card(
        shape = shape,
        border = BorderStroke(1.dp, MaterialTheme.appColors.cardBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(
                    top = adpSizeForFormat(
                        phone = 24.dp,
                        phoneLandscape = 8.dp
                    ).value(),
                    bottom = adpSizeForFormat(
                        phone = 24.dp,
                        phoneLandscape = 8.dp
                    ).value()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(layout.resultStatsVSpacing.value()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(layout.resultStatsCardsSpacing.value()),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(layout.resultStatsCardsSpacing.value())
                    ) {
                        QuizResultCard(
                            metric = metrics[0],
                            onClick = { selectedMetricType = QuizResultMetricType.TOTAL },
                            modifier = Modifier.weight(1f)
                        )
                        QuizResultCard(
                            metric = metrics[1],
                            onClick = { selectedMetricType = QuizResultMetricType.SCORE },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(layout.resultStatsCardsSpacing.value())
                    ) {
                        QuizResultCard(
                            metric = metrics[2],
                            onClick = { selectedMetricType = QuizResultMetricType.CORRECT },
                            modifier = Modifier.weight(1f)
                        )
                        QuizResultCard(
                            metric = metrics[3],
                            onClick = { selectedMetricType = QuizResultMetricType.ERRORS },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                if (results.skipped > 0) {
                    Text(
                        text = "Skipped questions: ${results.skipped}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 0.dp)
                    )
                }
            }


            Button(
                onClick = onRestartClick,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .widthIn(max = 400.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = Strings.flashcardRestart, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun QuizResultCard(
    metric: QuizResultMetric,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val layout = MaterialTheme.layout.quiz
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = MaterialTheme.appColors.quiz.metricBorder,
                shape = shape
            )
            .clip(shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(layout.resultMetricPadding.value()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                Box(
                    modifier = Modifier
                        .size(layout.resultStatsIconSize.value())
                        .clip(CircleShape)
                        .background(metric.colorSet.background()),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = metric.icon,
                        contentDescription = null,
                        tint = metric.colorSet.icon(),
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxSize(),
                    )
                }
                Text(
                    text = metric.value,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = metric.colorSet.text(),
                    maxLines = 1,
                )
            }

            Spacer(
                modifier = Modifier.height(
                    adpSizeForFormat(
                        phone = 6.dp,
                        phoneLandscape = 2.dp
                    ).value()
                )
            )

            Text(
                text = metric.label,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private data class QuizResultMetric(
    val type: QuizResultMetricType,
    val label: String,
    val value: String,
    val icon: ImageVector,
    val colorSet: AppColorSet
)
