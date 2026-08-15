package com.exploramus.shared.viewmodel.screens.details.detailpager

import com.exploramus.data.repository.functions.getFavoritesWithDetails
import com.exploramus.shared.viewmodel.core.FavoritesPagerScreenParams
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.details.toDetailsState
import kotlin.uuid.Uuid

fun StateManager.initFavoritesDetailsPager(params: FavoritesPagerScreenParams) = ScreenInitSettings(
    title = params.screenTitle ?: "",
    initState = { _ -> DetailsPagerScreenState(isLoading = true, resetKey = Uuid.random().toString()) },
    callOnInit = {
        val countryCode = params.countryCode ?: return@ScreenInitSettings

        val detailsList = dataRepository.getFavoritesWithDetails().toDetailsState()
        val initialIndex = detailsList.indexOfFirst { it.id == countryCode }.coerceAtLeast(0)

        updateScreen(DetailsPagerScreenState::class) {
            it.copy(
                isLoading = false,
                initialIndex = initialIndex,
                detailsList = detailsList,
            )
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true
)
