package com.muralex.exploramus.viewmodel.screens.favorites

import com.muralex.data.repository.functions.getFavorites
import com.muralex.exploramus.viewmodel.core.StateManager
import com.muralex.exploramus.viewmodel.core.CallOnInitValues
import com.muralex.exploramus.viewmodel.core.ScreenInitSettings

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
