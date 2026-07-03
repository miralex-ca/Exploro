package com.exploramus.shared.viewmodel.screens.details.singledetail

import com.exploramus.data.repository.functions.addFavorite
import com.exploramus.data.repository.functions.isFavorite
import com.exploramus.data.repository.functions.removeFavorite
import com.exploramus.shared.viewmodel.core.Events

fun Events.toggleFavorite(code: String) = screenCoroutine {
    val newValue = !dataRepository.isFavorite(code)

    if (newValue) {
        dataRepository.addFavorite(code)
    } else {
        dataRepository.removeFavorite(code)
    }

    stateManager.updateScreen(DetailsScreenState::class) {
        it.copy(
            details = it.details?.copy(isFavorite = newValue),
        )
    }
}