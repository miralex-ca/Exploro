package com.exploramus.app.composables.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.FadeInScreenContent
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.navigation.ui.topbars.DetailsPagerTopBar
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isLandscape
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.shared.viewmodel.screens.details.CountryDetailsState
import com.exploramus.shared.viewmodel.screens.details.detailpager.DetailsPagerScreenState

@Composable
fun DetailsPagerScreen(
    screenState: DetailsPagerScreenState,
    eventHandler: DetailsPagerEventHandler
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
                DetailsPagerScreenContent(
                    screenState = screenState,
                    details = details,
                    onEvent = eventHandler::onEvent
                )
            }
        }
    }
}

@Composable
fun DetailsPagerScreenContent(
    screenState: DetailsPagerScreenState,
    details: CountryDetailsState,
    onEvent: (DetailsPagerUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val isLandscape = formFactor.isLandscape
    val layout = MaterialTheme.layout.details

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            DetailsPagerTopBar(
                title = details.name,
                mapsUrl = details.mapsUrl,
                wikiUrl = details.wikiUrl,
                onBackClick = {  onEvent(DetailsPagerUiEvent.OnBackClicked) },
                onPreviousClick = { onEvent(DetailsPagerUiEvent.OnPreviousClicked) },
                onNextClick = { onEvent(DetailsPagerUiEvent.OnNextClicked) },
                hasPrevious = screenState.hasPrevious,
                hasNext = screenState.hasNext,
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
                            onFavoriteClick = {
                                onEvent(DetailsPagerUiEvent.ToggleFavorite(details.id))
                            }
                        )
                    } else {
                        DetailsSections(
                            details = details,
                            onFavoriteClick = {
                                onEvent(DetailsPagerUiEvent.ToggleFavorite(details.id))
                            }
                        )
                    }
                }
            }
        }
    }
}




