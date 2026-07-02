package com.exploramus.shared.viewmodel.screens.details.detailpager

import com.exploramus.data.repository.functions.addFavorite
import com.exploramus.data.repository.functions.isFavorite
import com.exploramus.data.repository.functions.removeFavorite
import com.exploramus.shared.viewmodel.core.Events

fun Events.toggleFavoritePager(code: String) = screenCoroutine {
    val newValue = !dataRepository.isFavorite(code)

    if (newValue) {
        dataRepository.addFavorite(code)
    } else {
        dataRepository.removeFavorite(code)
    }

    stateManager.updateScreen(DetailsPagerScreenState::class) {
        val updatedList = it.detailsList.map { details ->
            if (details.id == code) details.copy(isFavorite = newValue) else details
        }
        it.copy(detailsList = updatedList)
    }
}