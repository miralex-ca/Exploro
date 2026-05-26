package com.muralex.myapp.screens.favorites

import com.muralex.myapp.navigation.DetailsNavParams
import com.muralex.myapp.navigation.ScreenNavActions
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.favorites.FavoriteListItem
import com.muralex.myapp.viewmodel.screens.favorites.removeFavoriteBySwipe

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
            is FavoritesUiEvent.RemoveFavorite -> events.removeFavoriteBySwipe(event.countryId)
        }
    }
}

fun FavoriteListItem.toDetailsNavParams() = DetailsNavParams(id, name)

