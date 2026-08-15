package com.exploramus.shared.viewmodel.screens.favorites

import com.exploramus.data.repository.functions.getFavorites
import com.exploramus.data.repository.functions.removeFavorite
import com.exploramus.shared.viewmodel.core.Events

fun Events.removeFromFavorites(code: String) = screenCoroutine {
    dataRepository.removeFavorite(code)
    val favorites = dataRepository.getFavorites().toFavoriteItems()
    stateManager.updateScreen(FavoritesScreenState::class) {
        it.copy(
            favorites = favorites,
        )
    }
}
