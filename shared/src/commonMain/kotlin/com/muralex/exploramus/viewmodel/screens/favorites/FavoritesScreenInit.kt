package com.muralex.exploramus.viewmodel.screens.favorites

import com.muralex.data.repository.functions.getFavorites
import com.muralex.exploramus.viewmodel.StateManager
import com.muralex.exploramus.viewmodel.screens.CallOnInitValues
import com.muralex.exploramus.viewmodel.screens.ScreenInitSettings

fun StateManager.initFavoritesScreen() = ScreenInitSettings(
    title = "Favorites",
    initState = { FavoritesScreenState(isLoading = true) },
    callOnInit = {
        val favorites = dataRepository.getFavorites().toFavoriteItems()
        updateScreen(FavoritesScreenState::class) {
            it.copy(
                isLoading = false,
                favorites = favorites
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN
)
