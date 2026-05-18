package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.mappers.toListItems
import com.muralex.models.CountryListItem

suspend fun Repository.getCountriesBySectionId(sectionId: String): List<CountryListItem> = withRepoContext {
    localDb.getAllCountriesBySectionId(sectionId).toListItems()
}

suspend fun Repository.searchCountries(query: String): List<CountryListItem> = withRepoContext {
    localDb.searchCountries(query).toListItems()
}
