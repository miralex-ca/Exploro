package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.data.functions.getCountryInfo
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable
data class CountryDetailParams(val countryName: String) : ScreenParams

fun StateManager.initCountryDetail(params: CountryDetailParams) = ScreenInitSettings(
    title = params.countryName,
    initState = { CountryDetailState(isLoading = true) },
    callOnInit = {

        val countryInfo = dataRepository.getCountryInfo(params.countryName)

        updateScreen(CountryDetailState::class) {
            it.copy(
                isLoading = false,
                countryInfo = CountryDetailInfo(
                    countryInfo._listData,
                    countryInfo._extraData
                ),
            )
        }
    },
)