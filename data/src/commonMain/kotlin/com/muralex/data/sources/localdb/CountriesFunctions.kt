package com.muralex.data.sources.localdb

//import appLocal.AppDatabase
//import appLocal.GetCountriesWithUserData
//import com.muralex.models.Country
//import com.muralex.models.CountryUserData

//fun AppDatabase.getCountriesList(): List<Country> {
//    return countriesQueries.getCountriesList(mapper = ::Country).executeAsList()
//}
//
//fun AppDatabase.setCountriesList(list: List<Country>) {
//    countriesQueries.transaction {
//        list.forEach {
//            countriesQueries.upsertCountry(
//                id = it.id,
//                name = it.name,
//                population = it.population,
//            )
//        }
//    }
//}
//
//fun AppDatabase.getCountriesWithUserData(): List<CountryUserData> {
//    return favoritesQueries.getCountriesWithUserData().executeAsList().map { it.toDomain() }
//}
//
//fun GetCountriesWithUserData.toDomain(): CountryUserData = CountryUserData(
//    country = Country(id = id, name = name, population = population),
//    isFavorite = isFavorite == 1L
//)