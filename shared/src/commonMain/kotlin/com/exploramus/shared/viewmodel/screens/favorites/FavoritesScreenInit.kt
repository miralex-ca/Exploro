package com.exploramus.shared.viewmodel.screens.favorites

import com.exploramus.data.repository.functions.getFavorites
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager

fun StateManager.initFavoritesScreen() = ScreenInitSettings(
    title = "Favorites",
    initState = { FavoritesScreenState(isLoading = true) },
    callOnInit = {
        val favorites = dataRepository.getFavorites().toFavoriteItems()
        updateScreen(FavoritesScreenState::class) {
            it.copy(
                isLoading = false,
                favorites = favorites,
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN
)
