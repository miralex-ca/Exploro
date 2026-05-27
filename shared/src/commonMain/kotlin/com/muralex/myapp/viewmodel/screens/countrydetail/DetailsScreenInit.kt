package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.data.repository.functions.getCountryDetails
import com.muralex.data.repository.functions.isFavorite
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
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