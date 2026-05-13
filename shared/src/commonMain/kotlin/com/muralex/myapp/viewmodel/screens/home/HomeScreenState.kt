package com.muralex.myapp.viewmodel.screens.home

import com.muralex.models.CountryListItem
import com.muralex.models.HomeSection
import com.muralex.myapp.viewmodel.ScreenState

data class HomeScreenState (
    val isLoading : Boolean = false,
    val countriesListItems : List<CountryListItem> = emptyList(),
    val homeSections : List<HomeSection> = emptyList(),

    ): ScreenState

