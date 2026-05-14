package com.muralex.myapp.viewmodel.screens.countriesList

import com.muralex.data.functions.getCountriesListData
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

// INIZIALIZATION settings for this screen
// this is what should be implemented:
// - a data class implementing the ScreenParams interface, which defines the parameters to the passed to the screen
// - Navigation extension function taking the ScreenParams class as an argument, return the ScreenInitSettings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

@Serializable // Note: ScreenParams should always be set as Serializable
data class CountriesListParams(val listType: CountriesListType) : ScreenParams

fun StateManager.initCountriesList() = ScreenInitSettings(
    title = "Countries",
    initState = { CountriesListState(isLoading = true) },
    callOnInit = {



        val listData = dataRepository.getCountriesListData()
//        val favorites = dataRepository.getFavoriteCountries()
//
//
//        updateScreen(CountriesListState::class) {
//            it.copy(
//                isLoading = false,
//                countriesListItems = listData.map { CountriesListItem( it) },
//                favoriteCountries = favorites.map { CountriesListItem( it) },
//            )
//        }

       // delay(3000)

        updateScreen(CountriesListState::class) {
            it.copy(
                isLoading = false
            )
        }
    }
)