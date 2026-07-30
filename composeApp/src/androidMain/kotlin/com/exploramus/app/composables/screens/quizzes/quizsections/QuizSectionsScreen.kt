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
import androidx.compose.ui.unit.sp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.screens.quizzes.utils.toAppColorSet
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
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
                        is QuizSectionState.Favorites -> {
                            val title = Strings.quizCollectionFavorites
                            FavoritesSectionCard(
                                itemsCount = section.itemsCount,
                                onClick = {
                                    onEvent(QuizSectionsUiEvent.OnFavoritesClicked(title))
                                },
                            )
                        }
                        is QuizSectionState.AllCountries -> {
                            val title = Strings.quizCollectionAll
                            AllCountriesSectionCard(
                                itemsCount = section.itemsCount,
                                onClick = {
                                    onEvent(QuizSectionsUiEvent.OnAllCountriesClicked(title))
                                },
                            )
                        }
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
    val layout = MaterialTheme.layout.quizzesSection
    val colors = QuizzesSectionType.FAVORITES.toAppColorSet()
    QuizSectionCard(
        title = Strings.quizCollectionFavorites,
        subtitle = Strings.quizCollectionItemsCount(itemsCount),
        onClick = onClick,
        iconContent = {
            SectionIconBox(color = colors.background()) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = colors.icon(),
                    modifier = Modifier.size(layout.iconSize.value()),
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
    val layout = MaterialTheme.layout.quizzesSection
    val colors = QuizzesSectionType.ALL_COUNTRIES.toAppColorSet()
    QuizSectionCard(
        title = Strings.quizCollectionAll,
        subtitle = Strings.quizCollectionItemsCount(itemsCount),
        onClick = onClick,
        iconContent = {
            SectionIconBox(color = colors.background()) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = null,
                    tint = colors.icon(),
                    modifier = Modifier.size(layout.iconSize.value()),
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
    val layout = MaterialTheme.layout.quizzesSection

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
    ) {
        Column (
            modifier = Modifier.padding(bottom = 14.dp),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            ) {
                SectionIconBox(color = colors.background()) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = null,
                        tint = colors.icon(),
                        modifier = Modifier.size(layout.iconSize.value()),
                    )
                }
                Spacer(modifier = Modifier.width(13.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = Strings.quizCollectionContinents,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        fontSize = layout.titleFontSize.value()
                    )
                    Text(
                        text = Strings.quizCollectionContinentsDesc,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 4.dp, bottom = 12.dp),
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
            .padding(start = 28.dp, end = 16.dp)
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
                text = Strings.quizCollectionItemsCount(continent.itemsCount),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 13.sp,
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
    val layout = MaterialTheme.layout.quizzesSection
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
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = layout.titleFontSize.value()
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 13.sp,
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
    val layout = MaterialTheme.layout.quizzesSection
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(layout.imageSize.value())
            .clip(RoundedCornerShape(12.dp))
            .background(color),
        content = content,
    )
}




