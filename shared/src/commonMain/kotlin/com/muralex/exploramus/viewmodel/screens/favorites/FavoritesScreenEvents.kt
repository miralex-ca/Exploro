package com.muralex.exploramus.viewmodel.screens.favorites

import com.muralex.data.repository.functions.getFavorites
import com.muralex.data.repository.functions.removeFavorite
import com.muralex.exploramus.viewmodel.Events

fun Events.removeFavoriteBySwipe(code: String) = screenCoroutine {
    dataRepository.removeFavorite(code)

    stateManager.updateScreen(FavoritesScreenState::class) {
        it.copy(
            favorites = dataRepository.getFavorites().toFavoriteItems()
        )
    }
}