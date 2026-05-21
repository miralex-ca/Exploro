package com.muralex.myapp.viewmodel.screens.section

import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.ScreenState

data class SectionScreenState(
    val isLoading: Boolean = false,
    val countries: List<CountryListItem> = emptyList(),
) : ScreenState

