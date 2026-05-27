package com.muralex.exploramus.viewmodel.screens.countrydetail

import com.muralex.data.repository.functions.getCountryDetails
import com.muralex.data.repository.functions.isFavorite
import com.muralex.exploramus.viewmodel.ScreenParams
import com.muralex.exploramus.viewmodel.StateManager
import com.muralex.exploramus.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable
data class DetailsScreenParams(val countryCode: String? = null, val screenTitle: String? = null) : ScreenParams

fun StateManager.initCountryDetail(params: DetailsScreenParams) = ScreenInitSettings(
    title = params.screenTitle ?: "",
    initState = { DetailsScreenState(isLoading = true) },
    callOnInit = {
        val countryCode = params.countryCode ?: return@ScreenInitSettings

        val countryDetailsState = dataRepository.getCountryDetails(countryCode)?.toDetailsState()

        val details = countryDetailsState?.copy(
            isFavorite = dataRepository.isFavorite(countryCode)
        )

        updateScreen(DetailsScreenState::class) {
            it.copy(
                isLoading = false,
                details = details
            )
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true
)