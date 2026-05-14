package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.models.Country
import com.muralex.models.CountryExtraInfo
import com.muralex.models.CountryFull
import com.muralex.myapp.viewmodel.ScreenState

data class DetailsState (
    val isLoading: Boolean = false,
    val countryDetails : CountryFull? = null,
): ScreenState


data class CountryDetailInfo (
    val _listData : Country,
    val _extraData : CountryExtraInfo? = null,
) {

}