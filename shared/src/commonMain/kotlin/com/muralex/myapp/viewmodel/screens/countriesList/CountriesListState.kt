package com.muralex.myapp.viewmodel.screens.countriesList

import com.muralex.models.Country
import com.muralex.myapp.viewmodel.ScreenState

data class CountriesListState (
    val isLoading : Boolean = false,
    val countriesListItems : List<CountriesListItem> = emptyList(),
    val favoriteCountries : List<CountriesListItem> = emptyList(),
): ScreenState

/********** property classes **********/

enum class CountriesListType { ALL, FAVORITES }

data class CountriesListItem (
    val _data : Country,
) {
    // in the ViewModel classes, our computed properties only do UI-formatting operations
    // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
    val name = _data.name
}