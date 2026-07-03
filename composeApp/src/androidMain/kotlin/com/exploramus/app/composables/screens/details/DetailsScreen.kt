package com.exploramus.app.composables.screens.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.FadeInScreenContent
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.navigation.ui.topbars.DetailsTopBar
import com.exploramus.app.composables.screens.details.DetailsUiEvent.ToggleFavorite
import com.exploramus.app.composables.screens.details.views.DetailHeaderSection
import com.exploramus.app.composables.screens.details.views.DetailsInfoSection
import com.exploramus.app.composables.screens.details.views.LargeDetailsHeaderSection
import com.exploramus.app.composables.screens.details.views.LargeDetailsInfoSection
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.details.CountryDetailsState
import com.exploramus.shared.viewmodel.screens.details.singledetail.DetailsScreenState

@Composable
fun DetailsScreen(
    screenState: DetailsScreenState,
    eventHandler: DetailsEventHandler
) {
    val details = screenState.details

    FadeInScreenContent {
        when {
            screenState.isLoading -> {
                ScreenLoading()
            }

            details == null -> {
                EmptyStateView(EmptyState.NotFound)
            }

            else -> {
                DetailsScreenContent(
                    details = details,
                    onEvent = eventHandler::onEvent
                )
            }
        }
    }
}

@Composable
fun DetailsScreenContent(
    details: CountryDetailsState,
    onEvent: (DetailsUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val layout = MaterialTheme.layout.details

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            DetailsTopBar(
                title = details.name,
                mapsUrl = details.mapsUrl,
                wikiUrl = details.wikiUrl,
                onBackClick = {  onEvent(DetailsUiEvent.OnBackClicked) }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = layout.maxWidth.value())
                        .padding(horizontal = layout.horizontalPadding.value())
                        .padding(top = layout.topPadding.value(), bottom = layout.bottomPadding.value())
                ) {
                    if (isLandscape) {
                        LargeDetailsSections(
                            details = details,
                            onFavoriteClick = { onEvent(ToggleFavorite(details.id)) }
                        )
                    } else {
                        DetailsSections(
                            details = details,
                            onFavoriteClick = { onEvent(ToggleFavorite(details.id)) }
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun DetailsSections(
    details: CountryDetailsState,
    onFavoriteClick: () -> Unit,
) {
    val cornerRadius = MaterialTheme.layout.details.cardCorner.value()

    DetailsCard(
        shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius),
        modifier = Modifier.fillMaxWidth()
    ) {
        DetailHeaderSection(
            details = details,
            onFavoriteClick = onFavoriteClick
        )
    }

    DetailsCard(
        shape = RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.layout.cardSpacing.value(),
                bottom = 60.dp
            )
    ) {
        DetailsInfoSection(details)
    }
}

@Composable
fun LargeDetailsSections(
    details: CountryDetailsState,
    onFavoriteClick: () -> Unit,
) {
    val cornerRadius = MaterialTheme.layout.details.cardCorner.value()

    DetailsCard(
        shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius),
        modifier = Modifier.fillMaxWidth()
    ) {
        LargeDetailsHeaderSection(
            details = details,
            onFavoriteClick = onFavoriteClick
        )
    }

    DetailsCard(
        shape = RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.layout.cardSpacing.value(),
                bottom = 60.dp
            )
    ) {
        LargeDetailsInfoSection(details)
    }
}

@Composable
fun DetailsCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit
) {
    Surface(
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        shadowElevation = 0.dp,
        border = BorderStroke(0.5.dp, MaterialTheme.appColors.cardBorder),
        modifier = modifier,
        content = content
    )
}


