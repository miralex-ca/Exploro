package com.exploramus.app.composables.screens.favorites

import androidx.compose.runtime.Composable
import com.exploramus.app.composables.screens.favorites.views.FavoritesGrid
import com.exploramus.app.composables.screens.favorites.views.FavoritesList
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isCompact
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.FadeLoadingContent
import com.exploramus.shared.viewmodel.screens.favorites.FavoritesScreenState


@Composable
fun FavoritesScreen(
    screenState: FavoritesScreenState,
    eventHandler: FavoritesEventHandler
) {
    FadeLoadingContent(isLoading = screenState.isLoading) {
        FavoritesScreenContent(
            screenState = screenState,
            onEvent = eventHandler::onEvent,
        )
    }
}

@Composable
fun FavoritesScreenContent(
    screenState: FavoritesScreenState,
    onEvent: (FavoritesUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val useGrid = !formFactor.isCompact

    if (screenState.favorites.isEmpty()) {
        EmptyStateView(EmptyState.EmptyList)
    } else {
        if (useGrid) {
            FavoritesGrid(
                gridItems = screenState.favorites,
                onEvent = onEvent
            )
        } else {
            FavoritesList(
                listItems = screenState.favorites,
                onEvent = onEvent
            )
        }
    }
}
