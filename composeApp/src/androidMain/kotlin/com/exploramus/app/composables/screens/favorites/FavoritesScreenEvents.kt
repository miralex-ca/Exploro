package com.exploramus.app.composables.screens.favorites

import com.exploramus.app.composables.navigation.controller.DetailsNavParams
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.favorites.FavoriteListItem
import com.exploramus.shared.viewmodel.screens.favorites.removeFromFavorites

sealed class FavoritesUiEvent {
    data class RemoveFavorite(val countryId: String) : FavoritesUiEvent()
    data class OnItemClicked(val item: FavoriteListItem) : FavoritesUiEvent()
}

class FavoritesEventHandler(
    val navActions: ScreenNavActions,
    val events: Events
) {
    fun onEvent(event: FavoritesUiEvent) {
        when (event) {
            is FavoritesUiEvent.OnItemClicked -> navActions.toDetailFromList(event.item.toDetailsNavParams())
            is FavoritesUiEvent.RemoveFavorite -> events.removeFromFavorites(event.countryId)
        }
    }
}

fun FavoriteListItem.toDetailsNavParams() = DetailsNavParams(id, name)

