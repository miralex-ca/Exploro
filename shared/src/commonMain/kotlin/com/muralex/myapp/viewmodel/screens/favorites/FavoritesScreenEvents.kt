package com.muralex.myapp.viewmodel.screens.favorites

import com.muralex.data.repository.functions.getFavorites
import com.muralex.data.repository.functions.removeFavorite
import com.muralex.myapp.viewmodel.Events

fun Events.removeFavoriteBySwipe(code: String) = screenCoroutine {
    dataRepository.removeFavorite(code)

    stateManager.updateScreen(FavoritesScreenState::class) {
        it.copy(
            favorites = dataRepository.getFavorites()
        )
    }
}