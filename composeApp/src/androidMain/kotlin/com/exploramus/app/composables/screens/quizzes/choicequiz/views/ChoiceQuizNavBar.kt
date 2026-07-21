package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.core.models.ChoiceQuizNavigationMode
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizResultsState

@Composable
fun ChoiceQuizNavBar(
    results: ChoiceQuizResultsState,
    currentIndex: Int,
    totalCount: Int,
    isOptionSelected: Boolean,
    navigationMode: ChoiceQuizNavigationMode,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
    isVertical: Boolean = false,
) {
    val isLastPage = currentIndex == totalCount - 1
    val buttonText = if (isLastPage) "Finish" else "Next"

    val showButton = navigationMode == ChoiceQuizNavigationMode.MANUAL
    val isEnabled = !isLastPage || isOptionSelected

    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        if (isVertical) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = if (showButton) 0.dp else 20.dp)
                    .padding(top = if (showButton) 20.dp else 0.dp, bottom = if (showButton) 6.dp else 0.dp)
                    .padding(horizontal = 6.dp)
                    .width(52.dp)
                    .heightIn(min = 240.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(if (showButton) 4.dp else 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    StatItem(
                        count = results.correctCount,
                        icon = getChoiceQuizIconPainter(true),
                        color = AppColorPalette.GreenCorrect.text()
                    )

                    StatItem(
                        count = results.incorrectCount,
                        icon = getChoiceQuizIconPainter(false),
                        color = AppColorPalette.RedIncorrect.text()
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .width(24.dp)
                            .padding(vertical = 6.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.heightIn(min = 60.dp)
                    ) {
                        Text(
                            text = "${currentIndex + 1}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                        )
                        HorizontalDivider(
                            modifier = Modifier.width(16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        )
                        Text(
                            text = "$totalCount",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                if (showButton) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ContinueButton(
                        text = buttonText,
                        showIcon = !isLastPage,
                        enabled = isEnabled,
                        onClick = onContinueClick,
                        isVertical = true
                    )
                }
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = if (showButton) 0.dp else 20.dp)
                    .padding(start = if (showButton) 20.dp else 0.dp, end = if (showButton) 6.dp else 0.dp)
                    .padding(vertical = 6.dp)
                    .height(44.dp)
                    .widthIn(min = 240.dp),
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(if (showButton) 4.dp else 14.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    StatItem(
                        count = results.correctCount,
                        icon = getChoiceQuizIconPainter(true),
                        color = AppColorPalette.GreenCorrect.text()
                    )

                    StatItem(
                        count = results.incorrectCount,
                        icon = getChoiceQuizIconPainter(false),
                        color = AppColorPalette.RedIncorrect.text()
                    )

                    VerticalDivider(
                        modifier =
                        Modifier
                            .height(24.dp)
                            .padding(horizontal = 6.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )

                    Text(
                        text = "${currentIndex + 1} / $totalCount",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .widthIn(min = 60.dp)
                        // .padding(horizontal = 6.dp)
                    )
                }

                if (showButton) {
                    Spacer(modifier = Modifier.width(16.dp))
                    ContinueButton(
                        text = buttonText,
                        showIcon = !isLastPage,
                        enabled = isEnabled,
                        onClick = onContinueClick
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    count: Int,
    icon: Painter,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = color,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .widthIn(min = 20.dp)
        )
    }
}

@Composable
private fun ContinueButton(
    text: String,
    showIcon: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isVertical: Boolean = false,
) {

    if (isVertical) {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            modifier = modifier
                .size(44.dp)
        ) {
            val icon = if (showIcon) {
                Icons.AutoMirrored.Rounded.KeyboardArrowRight
            } else {
                Icons.Rounded.Check
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    } else {
        val contentPadding = PaddingValues(
            start = if (showIcon) 28.dp else 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 8.dp,
        )

        Button(
            onClick = onClick,
            enabled = enabled,
            shape = CircleShape,
            contentPadding = contentPadding,
            modifier = modifier
                .height(44.dp)
                .widthIn(min = 120.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                if (showIcon) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
