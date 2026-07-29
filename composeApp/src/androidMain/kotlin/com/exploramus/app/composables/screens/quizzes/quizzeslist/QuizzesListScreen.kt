package com.exploramus.app.composables.screens.quizzes.quizzeslist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.screens.quizzes.quizzeslist.views.QuizzesListChoiceQuizSettingsDialog
import com.exploramus.app.composables.screens.quizzes.quizzeslist.views.QuizzesListFlashcardSettingsDialog
import com.exploramus.app.composables.screens.quizzes.utils.getIcon
import com.exploramus.app.composables.screens.quizzes.utils.toAppColorSet
import com.exploramus.app.composables.screens.quizzes.utils.toDescription
import com.exploramus.app.composables.screens.quizzes.utils.toTitle
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.AppTypography
import com.exploramus.app.design.theme.appColors
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.QuizItemStatus
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesListScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionHeaderState

@Composable
fun QuizzesListScreen(
    screenState: QuizzesListScreenState,
    eventHandler: QuizzesListEventHandler,
) {
    if (screenState.isLoading) {
        ScreenLoading()
    } else {
        QuizzesListContent(
            screenState = screenState,
            onEvent = eventHandler::onEvent,
        )

        screenState.flashcardConfig?.let { config ->
            QuizzesListFlashcardSettingsDialog(
                config = config,
                onConfigChanged = { eventHandler.onEvent(QuizzesListUiEvent.UpdateFlashcardConfig(it)) },
                onDismiss = { eventHandler.onEvent(QuizzesListUiEvent.ToggleFlashcardSettings(false)) }
            )
        }

        screenState.choiceQuizConfig?.let { config ->
            val quizType = screenState.choiceQuizType ?: return@let
            QuizzesListChoiceQuizSettingsDialog(
                quizType = quizType,
                config = config,
                onConfigChanged = { eventHandler.onEvent(QuizzesListUiEvent.UpdateChoiceQuizConfig(it, quizType)) },
                onDismiss = { eventHandler.onEvent(QuizzesListUiEvent.ToggleChoiceQuizSettings(false)) }
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item(key = "header") {
            Box(
                modifier = Modifier.widthIn(max = MaterialTheme.layout.quizzesSection.itemMaxWidth.value())
            ) {
                QuizzesListHeaderCard(sectionInfo = screenState.sectionInfo)
            }

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
                Box(
                    modifier = Modifier.widthIn(max = MaterialTheme.layout.quizzesSection.itemMaxWidth.value())
                ) {
                    QuizCard(
                        quiz = quiz,
                        psTarget = screenState.psTarget,
                        ipTarget = screenState.ipTarget,
                        onClick = {
                            onEvent(
                                QuizzesListUiEvent.OnQuizClicked(
                                    sectionId = screenState.sectionInfo.continentId ?: "",
                                    sectionType = screenState.sectionInfo.sectionType,
                                    title = screenState.sectionInfo.title,
                                    quizType = quiz.quizType
                                )
                            )
                        },
                        onSettingsClick = { onEvent(QuizzesListUiEvent.OnQuizSettingsClicked(quiz.quizId, quiz.quizType)) },
                    )
                }

            }
        }
    }
}


@Composable
fun QuizzesListHeaderCard(
    sectionInfo: QuizzesSectionHeaderState,
    onStatClick: (QuizItemStatus) -> Unit = {},
) {
    val colors = sectionInfo.sectionType.toAppColorSet(sectionInfo.continentId)
    val icon = sectionInfo.sectionType.getIcon()

    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colors.text().copy(alpha = 0.4f)
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = sectionInfo.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${sectionInfo.itemsCount} countries available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                SectionIconBox(
                    size = 66.dp,
                    cornerRadius = 16.dp,
                    color = colors.background(),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colors.icon(),
                        modifier = Modifier.size(32.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                StatsRow(
                    mastered = sectionInfo.masteredCount,
                    familiar = sectionInfo.familiarCount,
                    unknown =  sectionInfo.unknownCount,
                    onClick = onStatClick,
                )
            }
        }
    }
}

@Composable
private fun StatsRow(
    unknown: Int,
    familiar: Int,
    mastered: Int,
    onClick: (QuizItemStatus) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 420.dp), // caps chip growth on wide tablets
    ) {
        StatChip(
            count = mastered,
            label = "Mastered",
            status = QuizItemStatus.MASTERED,
            modifier = Modifier.weight(1f),
            onClick = { onClick(QuizItemStatus.MASTERED) },
        )
        StatChip(
            count = familiar,
            label = "Familiar",
            status = QuizItemStatus.FAMILIAR,
            modifier = Modifier.weight(1f),
            onClick = { onClick(QuizItemStatus.FAMILIAR) },
        )
        StatChip(
            count = unknown,
            label = "Unknown",
            status = QuizItemStatus.UNKNOWN,
            modifier = Modifier.weight(1f),
            onClick = { onClick(QuizItemStatus.UNKNOWN) },
        )
    }
}

@Composable
private fun StatChip(
    count: Int,
    label: String,
    status: QuizItemStatus,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colorSet = status.toAppColorSet()
    val color = colorSet.text()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(colorSet.background())
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.35f),
                shape = RoundedCornerShape(50),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = color),
                onClick = onClick,
            )
            .padding(vertical = 10.dp),
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color.copy(alpha = 0.85f),
        )
    }
}

@Composable
fun QuizCard(
    quiz: QuizState,
    psTarget: ChoiceQuizStudyTarget,
    ipTarget: ChoiceQuizStudyTarget,
    onClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    val colors = quiz.quizType.toAppColorSet()
    val icon = quiz.quizType.getIcon()

    val displayTitle = quiz.toTitle(psTarget, ipTarget)
    val displayDescription = quiz.toDescription()

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp, bottom = 8.dp, end = 12.dp)
            ,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 6.dp, bottom = 8.dp)
            ) {
                Text(
                    text = displayTitle,
                    style = AppTypography.quizCardTitle,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = displayDescription,
                    style = AppTypography.quizCardDescription,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 2,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 12.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Large icon in tinted rounded square
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.background()),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colors.icon(),
                        modifier = Modifier.size(28.dp),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Quiz Settings",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SectionIconBox(
    size: Dp = 44.dp,
    cornerRadius: Dp = 12.dp,
    color: Color,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(color),
        content = content,
    )
}


