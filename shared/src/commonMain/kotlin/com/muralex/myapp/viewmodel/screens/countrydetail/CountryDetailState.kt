package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.myapp.datalayer.objects.CountryExtraData
import com.muralex.myapp.datalayer.objects.CountryListData
import com.muralex.myapp.viewmodel.ScreenState
import com.muralex.myapp.viewmodel.utils.toCommaThousandString
import com.muralex.myapp.viewmodel.utils.toPercentageString

data class CountryDetailState (
    val isLoading: Boolean = false,
    val countryInfo : CountryInfo = CountryInfo(),
): ScreenState


/********** property classes **********/

data class CountryInfo (
    val _listData : CountryListData = CountryListData(),
    val _extraData : CountryExtraData? = CountryExtraData(),
) {
    // in the ViewModel classes, our computed properties only do UI-formatting operations
    // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
    val population = _listData.population.toCommaThousandString()
    val firstDoses = _listData.firstDoses.toCommaThousandString()
    val firstDosesPerc = _listData.firstDosesPercentageFloat.toPercentageString()
    val fullyVaccinated = _listData.fullyVaccinated.toCommaThousandString()
    val fullyVaccinatedPerc = _listData.fullyVaccinatedPercentageFloat.toPercentageString()
    val vaccinesList : List<String>? = _extraData?.vaccinesList
}