package com.exploramus.app.composables.screens.quizzes.quizzeslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.FadeInScreenContent
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.design.adaptive.value
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesListScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionHeaderState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType


// ─── Screen ───────────────────────────────────────────────────────────────────

@Composable
fun QuizzesListScreen(
    screenState: QuizzesListScreenState,
    eventHandler: QuizzesListEventHandler,
) {
    if (screenState.isLoading) {
        ScreenLoading()
    } else {
        FadeInScreenContent(durationMillis = 200) {
            QuizzesListContent(
                screenState = screenState,
                onEvent = eventHandler::onEvent,
            )
        }
    }
}

@Composable
fun QuizzesListContent(
    screenState: QuizzesListScreenState,
    onEvent: (QuizzesListUiEvent) -> Unit,
) {
    val layout = MaterialTheme.layout
    val formFactor = LocalFormFactor.current
    val bottomPadding = layout.home.bottomPadding.value() +
            if (formFactor.useBottomBar) 60.dp else 0.dp

    LazyColumn(
        contentPadding = PaddingValues(
            top = layout.home.topPadding.value(),
            bottom = bottomPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        item(key = "header") {
            QuizzesListHeaderCard(sectionInfo = screenState.sectionInfo)
        }

        if (screenState.quizzes.isEmpty()) {
            item(key = "empty") {
                EmptyStateView(EmptyState.EmptyList)
            }
        } else {
            items(
                items = screenState.quizzes,
                key = { it.quizId },
            ) { quiz ->
                QuizCard(
                    quiz = quiz,
                    onClick = { onEvent(QuizzesListUiEvent.OnQuizClicked(quiz.quizId)) },
                    onSettingsClick = { onEvent(QuizzesListUiEvent.OnQuizSettingsClicked(quiz.quizId)) },
                )
            }
        }
    }
}

// ─── Header Card ──────────────────────────────────────────────────────────────

@Composable
fun QuizzesListHeaderCard(sectionInfo: QuizzesSectionHeaderState) {
    val iconData = sectionInfo.sectionType.toIconData()

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = iconData.cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, bottom = 20.dp, end = 0.dp),
        ) {
            // Left: text content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sectionInfo.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = iconData.contentColor,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${sectionInfo.itemsCount} quizzes available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = iconData.contentColor.copy(alpha = 0.7f),
                )
            }

            // Right: large icon, slightly overflowing the bottom for visual interest
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(90.dp)
                    .padding(end = 16.dp),
            ) {
                Icon(
                    imageVector = iconData.icon,
                    contentDescription = null,
                    tint = iconData.iconTint,
                    modifier = Modifier.size(64.dp),
                )
            }
        }
    }
}

// ─── Quiz Card ────────────────────────────────────────────────────────────────

@Composable
fun QuizCard(
    quiz: QuizState,
    onClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    val iconData = quiz.quizType.toIconData()
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = onClick,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 0.dp),
        ) {
            // Left: title + description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = quiz.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 2,
                )
            }

            // Right: icon + settings button stacked
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .width(72.dp)
                    .padding(bottom = 0.dp),
            ) {
                // Large icon in tinted rounded square
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(iconData.background),
                ) {
                    Icon(
                        imageVector = iconData.icon,
                        contentDescription = null,
                        tint = iconData.tint,
                        modifier = Modifier.size(30.dp),
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Settings button
                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Quiz settings",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}

// ─── Icon / theme data ────────────────────────────────────────────────────────

private data class SectionIconData(
    val icon: ImageVector,
    val iconTint: Color,
    val cardBackground: Color,
    val contentColor: Color,
)

private fun QuizzesSectionType.toIconData(): SectionIconData = when (this) {
    QuizzesSectionType.FAVORITES -> SectionIconData(
        icon = Icons.Default.Star,
        iconTint = Color(0xFFF9A825).copy(alpha = 0.5f),
        cardBackground = Color(0xFFFFF8E1),
        contentColor = Color(0xFF5D4037),
    )
    QuizzesSectionType.ALL_COUNTRIES -> SectionIconData(
        icon = Icons.Default.Public,
        iconTint = Color(0xFF1E88E5).copy(alpha = 0.45f),
        cardBackground = Color(0xFFE3F2FD),
        contentColor = Color(0xFF0D47A1),
    )
    QuizzesSectionType.CONTINENT -> SectionIconData(
        icon = Icons.Default.Map,
        iconTint = Color(0xFFE91E63).copy(alpha = 0.45f),
        cardBackground = Color(0xFFFCE4EC),
        contentColor = Color(0xFF880E4F),
    )
}

private data class QuizIconData(
    val icon: ImageVector,
    val tint: Color,
    val background: Color,
)

private fun QuizType.toIconData(): QuizIconData = when (this) {
    QuizType.FLASHCARDS -> QuizIconData(
        icon = Icons.Default.Style,         // card/deck icon
        tint = Color(0xFF7B1FA2),           // deep purple
        background = Color(0xFFF3E5F5),     // light purple
    )
    QuizType.TEST_COUNTRY_CAPITAL -> QuizIconData(
        icon = Icons.Default.LocationCity,  // capital / city
        tint = Color(0xFF1565C0),           // dark blue
        background = Color(0xFFE3F2FD),     // light blue
    )
    QuizType.TEST_FLAG_COUNTRY -> QuizIconData(
        icon = Icons.Default.Flag,          // flag
        tint = Color(0xFF2E7D32),           // dark green
        background = Color(0xFFE8F5E9),     // light green
    )
}

