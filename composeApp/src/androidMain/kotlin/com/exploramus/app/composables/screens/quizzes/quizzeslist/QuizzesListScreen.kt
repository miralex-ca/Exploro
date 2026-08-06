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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.navigation.ui.topbars.TopBar
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
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.QuizItemStatus
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesListScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionHeaderState

@Composable
fun QuizzesListScreen(
    screenState: QuizzesListScreenState,
    eventHandler: QuizzesListEventHandler,
) {
    var showNoDataAlert by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = screenState.sectionInfo.title,
            onBackClick = { eventHandler.navActions.navigateBack() },
            actions = {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = Strings.commonMoreOptions
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(Strings.quizMenuResetProgress) },
                        onClick = {
                            showMenu = false
                            showResetDialog = true
                        }
                    )
                }
            }
        )

        Box(modifier = Modifier.weight(1f)) {
            if (screenState.isLoading) {
                ScreenLoading()
            } else {
                QuizzesListContent(
                    screenState = screenState,
                    onNoData = { showNoDataAlert = true },
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

                if (showNoDataAlert) {
                    AlertDialog(
                        onDismissRequest = { showNoDataAlert = false },
                        title = { Text(Strings.quizNoDataAlertTitle) },
                        text = {
                            Text(Strings.quizNoDataAlertText)
                        },
                        confirmButton = {
                            TextButton(onClick = { showNoDataAlert = false }) {
                                Text(Strings.commonClose)
                            }
                        },
                    )
                }

                if (showResetDialog) {
                    AlertDialog(
                        onDismissRequest = { showResetDialog = false },
                        title = {
                            Text(
                                text = Strings.quizResetProgressDialogTitle,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        text = {
                            Text(
                                text = Strings.quizResetProgressDialogText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showResetDialog = false
                                    eventHandler.onEvent(
                                        QuizzesListUiEvent.ResetProgress(
                                            sectionId = screenState.sectionInfo.sectionId,
                                            sectionType = screenState.sectionInfo.sectionType
                                        )
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text(Strings.commonReset)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showResetDialog = false }) {
                                Text(Strings.commonCancel)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun QuizzesListContent(
    screenState: QuizzesListScreenState,
    onNoData: () -> Unit,
    onEvent: (QuizzesListUiEvent) -> Unit,
) {
    val layout = MaterialTheme.layout
    val formFactor = LocalFormFactor.current
    val bottomPadding = layout.home.bottomPadding.value() +
            if (formFactor.useBottomBar) 60.dp else 0.dp

    val onQuizClick: (QuizState) -> Unit = { quiz ->
        if (screenState.sectionInfo.eligibleCount > 0) {
            onEvent(screenState.sectionInfo.toQuizClickedEvent(quiz.quizType))
        } else {
            onNoData()
        }
    }

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
                QuizzesListHeaderCard(
                    sectionInfo = screenState.sectionInfo,
                    onStatClick = { status ->
                        onEvent(screenState.sectionInfo.toMasteryStatsEvent(status))
                    }
                )
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
                        onClick = { onQuizClick(quiz) },
                        onSettingsClick = { onEvent(QuizzesListUiEvent.OnQuizSettingsClicked(quiz.quizId, quiz.quizType)) },
                    )
                }

            }
        }
    }
}

private fun QuizzesSectionHeaderState.toQuizClickedEvent(quizType: QuizType) =
    QuizzesListUiEvent.OnQuizClicked(
        sectionId = sectionId,
        sectionType = sectionType,
        title = title,
        quizType = quizType
    )

private fun QuizzesSectionHeaderState.toMasteryStatsEvent(status: QuizItemStatus?) =
    QuizzesListUiEvent.OnMasteryStatsClicked(
        sectionId = sectionId,
        sectionType = sectionType,
        status = status,
        title = title
    )


@Composable
fun QuizzesListHeaderCard(
    sectionInfo: QuizzesSectionHeaderState,
    onStatClick: (QuizItemStatus) -> Unit = {},
) {
    val colors = sectionInfo.sectionType.toAppColorSet(sectionInfo.sectionId)
    val icon = sectionInfo.sectionType.getIcon()
    val layout = MaterialTheme.layout.quizzesSection
    var showEligibilityInfo by remember { mutableStateOf(false) }

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

                    CountriesEligibilityLabel(
                        eligibleCount = sectionInfo.eligibleCount,
                        totalCount = sectionInfo.itemsCount,
                        onInfoClick = { showEligibilityInfo = true },
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                SectionIconBox(
                    size = layout.headerImage.value(),
                    cornerRadius = 16.dp,
                    color = colors.background(),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colors.icon(),
                        modifier = Modifier.size(layout.headerIcon.value()),
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                StatsRow(
                    mastered = sectionInfo.masteredCount,
                    familiar = sectionInfo.familiarCount,
                    unknown = sectionInfo.unknownCount,
                    onClick = onStatClick,
                )
            }
        }
    }

    if (showEligibilityInfo) {
        AlertDialog(
            onDismissRequest = { showEligibilityInfo = false },
            title = { Text(Strings.quizEligibilityInfoTitle) },
            text = {
                Text(Strings.quizEligibilityInfoText)
            },
            confirmButton = {
                TextButton(onClick = { showEligibilityInfo = false }) {
                    Text(Strings.commonGotIt)
                }
            },
        )
    }
}

@Composable
private fun CountriesEligibilityLabel(
    eligibleCount: Int,
    totalCount: Int,
    onInfoClick: () -> Unit,
) {
    val isPartiallyEligible = eligibleCount < totalCount
    val label = Strings.quizCollectionItemsCount(eligibleCount, totalCount)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = if (isPartiallyEligible) {
            Modifier
                .offset(x = (-8).dp)
                .clip(RoundedCornerShape(50))
                .clickable { onInfoClick() }
                .padding(horizontal = 8.dp, vertical = 4.dp)
        } else {
            Modifier
        },
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
        )

        if (isPartiallyEligible) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier.size(18.dp),
            )
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
    val allMastered = familiar == 0 && unknown == 0 && mastered > 0

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 420.dp), // caps chip growth on wide tablets
    ) {
        StatChip(
            count = mastered,
            label = Strings.quizStatMastered,
            status = QuizItemStatus.MASTERED,
            modifier = Modifier.weight(1f),
            onClick = { onClick(QuizItemStatus.MASTERED) },
        )


        if (allMastered) {
            AllMasteredBadge(modifier = Modifier.weight(2f))
        } else {
            StatChip(
                count = familiar,
                label = Strings.quizStatFamiliar,
                status = QuizItemStatus.FAMILIAR,
                modifier = Modifier.weight(1f),
                onClick = { onClick(QuizItemStatus.FAMILIAR) },
            )
            StatChip(
                count = unknown,
                label = Strings.quizStatUnknown,
                status = QuizItemStatus.UNKNOWN,
                modifier = Modifier.weight(1f),
                onClick = { onClick(QuizItemStatus.UNKNOWN) },
            )
        }
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
private fun AllMasteredBadge(modifier: Modifier = Modifier) {
    val colorSet = AppColorPalette.Marigold
    val text = colorSet.text()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(colorSet.background())
            .border(
                width = 1.dp,
                color = colorSet.icon().copy(alpha = 0.6f),
                shape = RoundedCornerShape(50),
            )
            .padding(vertical = 10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = text,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = "Amazing!", //Strings.quizAllMasteredHeadline, // "Brilliant!"
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            text = "All countries mastered!", //Strings.quizAllMasteredHint, // "All countries mastered!"
            style = MaterialTheme.typography.labelSmall,
            color = text,
            maxLines = 1,
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
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    maxLines = 1,
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = displayDescription,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
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
                        contentDescription = Strings.commonSettings,
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


