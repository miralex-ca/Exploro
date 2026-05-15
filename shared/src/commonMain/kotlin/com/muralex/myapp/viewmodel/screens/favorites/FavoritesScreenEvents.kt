package com.muralex.myapp.viewmodel.screens.favorites

import com.muralex.data.functions.getFavorites
import com.muralex.data.functions.removeFavorite
import com.muralex.myapp.viewmodel.Events


fun Events.toggleFavoriteBySwipe(code: String) = screenCoroutine {
    dataRepository.removeFavorite(code)

    stateManager.updateScreen(FavoritesScreenState::class) {
        it.copy(
            favorites = dataRepository.getFavorites()
        )
    }
}