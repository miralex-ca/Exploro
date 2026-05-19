package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.data.repository.functions.addFavorite
import com.muralex.data.repository.functions.isFavorite
import com.muralex.data.repository.functions.removeFavorite
import com.muralex.myapp.viewmodel.Events

fun Events.toggleFavorite(code: String) = screenCoroutine {
    val newValue = !dataRepository.isFavorite(code)

    if (newValue) {
        dataRepository.addFavorite(code)
    } else {
        dataRepository.removeFavorite(code)
    }

    stateManager.updateScreen(DetailsScreenState::class) {
        it.copy(
            isFavorite = newValue
        )
    }
}