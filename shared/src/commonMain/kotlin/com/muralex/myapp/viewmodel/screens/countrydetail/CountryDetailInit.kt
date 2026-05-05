package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.myapp.datalayer.functions.getCountryInfo
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable // Note: ScreenParams should always be set as Serializable
data class CountryDetailParams(val countryName: String) : ScreenParams

fun StateManager.initCountryDetail(params: CountryDetailParams) = ScreenInitSettings(
    title = params.countryName,
    initState = { CountryDetailState(isLoading = true) },
    callOnInit = {
        val countryInfo = dataRepository.getCountryInfo(params.countryName)
        // update state, after retrieving data from the repository
        updateScreen(CountryDetailState::class) {
            it.copy(
                isLoading = false,
                countryInfo = countryInfo,
            )
        }
    },
)