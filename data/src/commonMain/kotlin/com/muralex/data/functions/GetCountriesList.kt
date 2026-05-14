package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.CountryListItem

suspend fun Repository.getCountriesListData(): List<CountryListItem> = withRepoContext {
    localDb.getCountriesList()
}

suspend fun Repository.getCountriesByContinent(continent: String): List<CountryListItem> = withRepoContext {
    localDb.getAllCountriesByContinent(continent)
}
