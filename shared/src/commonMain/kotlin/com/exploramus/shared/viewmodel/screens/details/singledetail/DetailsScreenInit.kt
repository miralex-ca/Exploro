package com.exploramus.shared.viewmodel.screens.details.singledetail

import com.exploramus.data.repository.functions.getCountryDetails
import com.exploramus.data.repository.functions.isFavorite
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.details.toDetailsState
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