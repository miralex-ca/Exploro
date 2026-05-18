package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.mappers.toListItems
import com.muralex.models.CountryFull


suspend fun Repository.getCountryDetails(code: String):  CountryFull? = withRepoContext {
     localDb.getCountryDetailsById(code)
}

suspend fun Repository.isFavorite(code: String):  Boolean = withRepoContext {
     localDb.isFavorite(code)
}

suspend fun Repository.removeFavorite(code: String) = withRepoContext {
     localDb.removeFavorite(code)
}

suspend fun Repository.addFavorite(code: String) = withRepoContext {
     localDb.addFavorite(code)
}

suspend fun Repository.getFavorites() = withRepoContext {
     localDb.getFavorites().toListItems()
}