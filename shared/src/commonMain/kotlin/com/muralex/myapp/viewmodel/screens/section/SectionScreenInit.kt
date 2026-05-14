package com.muralex.myapp.viewmodel.screens.section

import com.muralex.data.functions.getCountriesByContinent
import com.muralex.data.functions.getCountriesListData
import com.muralex.data.functions.getHomeSections
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.home.HomeScreenState
import com.muralex.myapp.viewmodel.screens.home.bootstrapApp
import kotlinx.serialization.Serializable

@Serializable
data class SectionParams(val continent: String) : ScreenParams

fun StateManager.initSectionScreen(params: SectionParams) = ScreenInitSettings(
    title = "Section",
    initState = { SectionScreenState(isLoading = true) },
    callOnInit = {

        val countries = dataRepository.getCountriesByContinent(params.continent)

         updateScreen(SectionScreenState::class) {
            it.copy(
                isLoading = false,
                countries = countries
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL

)