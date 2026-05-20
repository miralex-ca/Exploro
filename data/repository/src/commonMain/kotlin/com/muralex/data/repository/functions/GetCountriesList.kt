package com.muralex.data.repository.functions

import com.muralex.data.repository.Repository
import com.muralex.mappers.toListItems
import com.muralex.models.CountryListItem

suspend fun Repository.getCountriesBySectionId(sectionId: String): List<CountryListItem> = withRepoContext {
    localDb.getAllCountriesBySectionId(sectionId).toListItems()
}

suspend fun Repository.searchCountries(query: String): List<CountryListItem> = withRepoContext {
    localDb.searchCountries(query).toListItems()
}

suspend fun Repository.hasCountriesData(): Boolean = withRepoContext {
    localDb.hasCountriesData()
}


