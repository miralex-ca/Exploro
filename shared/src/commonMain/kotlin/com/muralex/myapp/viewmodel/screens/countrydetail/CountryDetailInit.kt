package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.data.functions.getCountryDetails
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable
data class CountryDetailParams(val countryName: String, val countryCode: String? = null) : ScreenParams

fun StateManager.initCountryDetail(params: CountryDetailParams) = ScreenInitSettings(
    title = params.countryName,
    initState = { DetailsState(isLoading = true) },
    callOnInit = {

        if (params.countryCode != null) {
            val countryDetails = dataRepository.getCountryDetails(params.countryCode)

            updateScreen(DetailsState::class) {
                it.copy(
                    isLoading = false,
                    countryDetails = countryDetails,
                )
            }
        }

    },
)