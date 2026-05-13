package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryExtraInfo
import com.muralex.models.CountryFull
import com.muralex.myapp.viewmodel.ScreenState
import com.muralex.myapp.viewmodel.utils.toCommaThousandString

data class CountryDetailState (
    val isLoading: Boolean = false,
    val countryInfo : CountryFull? = null,
): ScreenState


data class CountryDetailInfo (
    val _listData : Country,
    val _extraData : CountryExtraInfo? = null,
) {

}