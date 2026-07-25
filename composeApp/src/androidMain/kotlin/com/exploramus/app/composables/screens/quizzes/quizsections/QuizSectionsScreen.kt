package com.exploramus.app.composables.screens.quizzes.quizsections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.screens.quizzes.utils.toAppColorSet
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.quizzes.quizsections.ContinentSectionState
import com.exploramus.shared.viewmodel.screens.quizzes.quizsections.QuizSectionState
import com.exploramus.shared.viewmodel.screens.quizzes.quizsections.QuizSectionsScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

@Composable
fun QuizSectionsScreen(
    screenState: QuizSectionsScreenState,
    eventHandler: QuizSectionsEventHandler
) {
    if (screenState.isLoading) {
        ScreenLoading()
    } else {
        QuizzesSectionsContent(
            screenState = screenState,
            onEvent = eventHandler::onEvent,
        )
    }
}

@Composable
fun QuizzesSectionsContent(
    screenState: QuizSectionsScreenState,
    onEvent: (QuizSectionsUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val layout = MaterialTheme.layout.quizzesSection
    val bottomPadding = layout.bottomPadding.value() +
            if (formFactor.useBottomBar) 60.dp else 0.dp

    if (screenState.quizzesSections.isEmpty()) {
        EmptyStateView(EmptyState.EmptyList)
    } else {
        LazyColumn(
            contentPadding = PaddingValues(
                top = layout.topPadding.value(),
                bottom = bottomPadding,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = layout.horizontalPadding.value()),
            ) {
            items(
                items = screenState.quizzesSections,
                key = { it.sectionId },
            ) { section ->
                Box(
                    modifier = Modifier.widthIn(max = layout.itemMaxWidth.value())
                ) {
                    when (section) {
                        is QuizSectionState.Favorites -> FavoritesSectionCard(
                            itemsCount = section.itemsCount,
                            onClick = { onEvent(QuizSectionsUiEvent.OnFavoritesClicked) },
                        )
                        is QuizSectionState.AllCountries -> AllCountriesSectionCard(
                            itemsCount = section.itemsCount,
                            onClick = { onEvent(QuizSectionsUiEvent.OnAllCountriesClicked) },
                        )
                        is QuizSectionState.Continents -> ContinentsSectionGroup(
                            section = section,
                            onContinentClick = { id, name ->
                                onEvent(
                                    QuizSectionsUiEvent.OnContinentClicked(
                                        id, name)
                                )
                            },
                        )
                    }
                }

            }
        }

    }
}

@Composable
fun FavoritesSectionCard(
    itemsCount: Int,
    onClick: () -> Unit,
) {
    val colors = QuizzesSectionType.FAVORITES.toAppColorSet()
    QuizSectionCard(
        title = "Favorites",
        subtitle = "$itemsCount countries",
        onClick = onClick,
        iconContent = {
            SectionIconBox(color = colors.background()) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = colors.icon(),
                    modifier = Modifier.size(24.dp),
                )
            }
        },
    )
}

@Composable
fun AllCountriesSectionCard(
    itemsCount: Int,
    onClick: () -> Unit,
) {
    val colors = QuizzesSectionType.ALL_COUNTRIES.toAppColorSet()
    QuizSectionCard(
        title = "All Countries",
        subtitle = "$itemsCount countries",
        onClick = onClick,
        iconContent = {
            SectionIconBox(color = colors.background()) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = null,
                    tint = colors.icon(),
                    modifier = Modifier.size(24.dp),
                )
            }
        },
    )
}

@Composable
fun ContinentsSectionGroup(
    section: QuizSectionState.Continents,
    onContinentClick: (String, String) -> Unit,
) {
    val colors = QuizzesSectionType.CONTINENT.toAppColorSet()

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            ) {
                SectionIconBox(color = colors.background()) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = null,
                        tint = colors.icon(),
                        modifier = Modifier.size(24.dp),
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Continents",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Browse quizzes for each continent",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            section.continents.forEachIndexed { _, section ->
                
                ContinentRow(
                    continent = section,
                    onClick = { onContinentClick(section.sectionId, section.sectionName) },
                )

            }
        }
    }
}

@Composable
fun ContinentRow(
    continent: ContinentSectionState,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = onClick,
            )
            .padding(start = 24.dp, end = 16.dp)
            .padding(vertical = 12.dp),
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(continent.sectionId.toAppColorSet().icon()),
        ) {
            Text(
                text = continent.sectionName.take(1),
                style = MaterialTheme.typography.titleSmall,
                color = continent.sectionId.toAppColorSet().onIcon(),
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = continent.sectionName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text = "${continent.itemsCount} countries",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        )
    }
}

@Composable
fun QuizSectionCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    iconContent: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth() ,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
        ) {
            iconContent()
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            )
        }
    }
}

@Composable
fun SectionIconBox(
    color: Color,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color),
        content = content,
    )
}




