package com.exploramus.shared.viewmodel.screens.details.detailpager

import com.exploramus.data.repository.functions.getCountriesBySectionId
import com.exploramus.data.repository.functions.getCountryDetails
import com.exploramus.data.repository.functions.isFavorite
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.details.toDetailsState
import kotlinx.serialization.Serializable

@Serializable
data class DetailsPagerScreenParams(val countryCode: String? = null, val continent: String? = null, val screenTitle: String? = null) : ScreenParams

fun StateManager.initDetailsPager(params: DetailsPagerScreenParams) = ScreenInitSettings(
    title = params.screenTitle ?: "",
    initState = { DetailsPagerScreenState(isLoading = true,) },
    callOnInit = {
        val countryCode = params.countryCode ?: return@ScreenInitSettings
        val continentCode = params.continent ?: return@ScreenInitSettings

        val countries = dataRepository.getCountriesBySectionId(continentCode)
        val initialIndex = countries.indexOfFirst { it.id == countryCode }.coerceAtLeast(0)

        val countryDetailsState = dataRepository.getCountryDetails(countryCode)?.toDetailsState()

        val details = countryDetailsState?.copy(
            isFavorite = dataRepository.isFavorite(countryCode)
        )

        updateScreen(DetailsPagerScreenState::class) {
            it.copy(
                isLoading = false,
                countries = countries,
                details = details,
                hasNext = true,
                hasPrevious = true,

                pagerController = DetailsPagerController(
                    initialIndex = initialIndex,
                    totalCount = countries.size,
                ),
            )
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true
)