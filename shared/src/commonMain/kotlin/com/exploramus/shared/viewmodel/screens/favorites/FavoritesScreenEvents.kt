package com.exploramus.shared.viewmodel.screens.favorites

import com.exploramus.data.repository.functions.addFavorite
import com.exploramus.data.repository.functions.getCountryDetails
import com.exploramus.data.repository.functions.getFavorites
import com.exploramus.data.repository.functions.isFavorite
import com.exploramus.data.repository.functions.removeFavorite
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.countrydetail.toDetailsState

fun Events.removeFavoriteBySwipe(code: String) = screenCoroutine {

    dataRepository.removeFavorite(code)


    var selected: String? = null

    stateManager.updateScreen(FavoritesScreenState::class) {
        selected = it.selectedCountry?.id
        it.copy(
            favorites = dataRepository.getFavorites().toFavoriteItems(),

        )
    }

    selected?.let {
        if (it == code) {
            val countryDetailsState = dataRepository.getCountryDetails(code)?.toDetailsState()
            stateManager.updateScreen(FavoritesScreenState::class) {
                it.copy(
                    selectedCountry = countryDetailsState
                )
            }

        }
    }

}


fun Events.updateDetailsInFavorites(code: String) = screenCoroutine {
    val newValue = !dataRepository.isFavorite(code)

    if (newValue) {
        dataRepository.addFavorite(code)
    } else {
        dataRepository.removeFavorite(code)
    }

    val countryDetailsState = dataRepository.getCountryDetails(code)?.toDetailsState()
    val favorites = dataRepository.getFavorites().toFavoriteItems()

    stateManager.updateScreen(FavoritesScreenState::class) {
        it.copy(
            favorites = favorites,
            selectedCountry = countryDetailsState
        )
    }
}

fun Events.selectFavoriteCountry(itemId: String) = screenCoroutine {
    val countryDetailsState = dataRepository.getCountryDetails(itemId)?.toDetailsState()
    stateManager.updateScreen(FavoritesScreenState::class) {
        it.copy(selectedCountry = countryDetailsState)
    }
}