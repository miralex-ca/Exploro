package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizNavigationMode
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
) {
    val isLastPage = currentIndex == totalCount - 1
    val buttonText = if (isLastPage) "Finish" else "Next"
    
    // Button is ONLY shown in MANUAL mode. In AUTO mode, everything is automated.
    val showButton = navigationMode == ChoiceQuizNavigationMode.MANUAL
    val isEnabled = !isLastPage || isOptionSelected

    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = if (showButton) 0.dp else 20.dp)
                .padding(start = if (showButton) 20.dp else 0.dp, end = if (showButton) 6.dp else 0.dp)
                .padding(vertical = 6.dp)
                .height(44.dp)
                .widthIn(min = 240.dp)
            ,
        ) {
            // Stats Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(if (showButton) 12.dp else 20.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                // Correct Counter
                StatItem(
                    count = results.correctCount,
                    icon = Icons.Rounded.Check,
                    color = Color(0xFF4CAF50)
                )

                // Error Counter
                StatItem(
                    count = results.incorrectCount,
                    icon = Icons.Rounded.Close,
                    color = MaterialTheme.colorScheme.error
                )

                // Divider
                VerticalDivider(
                    modifier = Modifier.height(24.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )

                // Progress Counter
                Text(
                    text = "${currentIndex + 1} / $totalCount",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // Continue Button
            if (showButton) {
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = onContinueClick,
                    enabled = isEnabled,
                    shape = CircleShape,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier
                        .height(44.dp)
                        .widthIn(min = 100.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = buttonText,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        if (!isLastPage) {
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
    }
}

@Composable
private fun StatItem(
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
