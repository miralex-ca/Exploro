package com.muralex.myapp.viewmodel.screens.countriesList

import com.muralex.myapp.datalayer.objects.CountryListData
import com.muralex.myapp.viewmodel.ScreenState
import com.muralex.myapp.viewmodel.utils.toPercentageString

data class CountriesListState (
    val isLoading : Boolean = false,
    val countriesListItems : List<CountriesListItem> = emptyList(),
    val favoriteCountries : Map<String,Boolean> = emptyMap(),
): ScreenState

/********** property classes **********/

enum class CountriesListType { ALL, FAVORITES }

data class CountriesListItem (
    val _data : CountryListData,
) {
    // in the ViewModel classes, our computed properties only do UI-formatting operations
    // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
    val name = _data.name
    val firstDosesPerc = _data.firstDosesPercentageFloat.toPercentageString()
    val fullyVaccinatedPerc = _data.fullyVaccinatedPercentageFloat.toPercentageString()
}