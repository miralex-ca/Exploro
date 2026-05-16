package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.data.functions.getCountryDetails
import com.muralex.data.functions.isFavorite
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable
data class CountryDetailParams(val countryName: String, val countryCode: String? = null) : ScreenParams

fun StateManager.initCountryDetail(params: CountryDetailParams) = ScreenInitSettings(
    title = params.countryName,
    initState = { DetailsScreenState(isLoading = true) },
    callOnInit = {
        if (params.countryCode != null) {
            val countryCode = params.countryCode
            val countryDetails = dataRepository.getCountryDetails(countryCode)

            val isFavorite = dataRepository.isFavorite(countryCode)

            updateScreen(DetailsScreenState::class) {
                it.copy(
                    isLoading = false,
                    countryDetails = countryDetails,
                    isFavorite = isFavorite,
                )
            }
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true

)