package com.muralex.myapp.viewmodel.screens

import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.countriesList.initCountriesList
import com.muralex.myapp.viewmodel.screens.countrydetail.initCountryDetail

enum class Screen(
    val asString: String,
    val navigationLevel : Int = 1,
    val initSettings: StateManager.(ScreenIdentifier) -> ScreenInitSettings,
) {
    CountriesList("countrieslist", 1, {
        initCountriesList(it.params())
    }),
    CountryDetail("country", 2, {
        initCountryDetail(it.params())
    })
}