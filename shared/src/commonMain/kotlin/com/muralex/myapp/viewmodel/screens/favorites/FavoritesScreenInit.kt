package com.muralex.myapp.viewmodel.screens.favorites

import com.muralex.data.repository.functions.getFavorites
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings

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
