package com.exploramus.shared

import com.exploramus.core.common.DispatchersProvider
import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.AppInfo
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.core.models.CountryWithDetails
import com.exploramus.data.common.AssetsDataSource
import com.exploramus.data.common.LocalDataSource
import com.exploramus.data.common.PlatformInfoProvider
import com.exploramus.data.common.RemoteDataSource
import com.exploramus.data.repository.Repository
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.Dispatchers

object TestFakes {

    val country = Country(
        id = "FRA", name = "France", officialName = "French Republic",
        capital = "Paris", continent = "Europe", subregion = "Western Europe",
        flagPngUrl = "", flagAlt = ""
    )

    val details = CountryDetails(
        id = "FRA", coatOfArmsPngUrl = "", area = 551695.0, population = 69081996,
        currencyCode = "EUR", currencyName = "Euro", currencySymbol = "€",
        languages = listOf("French"), mapsGoogleUrl = "", mapsOsmUrl = "",
        timezones = listOf("UTC+01:00"), wikipediaUrl = "", capitalLat = 48.87, capitalLng = 2.33
    )

    val successApiResult = DataResult.Success(Pair(listOf(country), listOf(details)))
    val errorApiResult: DataResult<Pair<List<Country>, List<CountryDetails>>> = DataResult.Error(null)

    open class FakeLocalDataSource(hasData: Boolean = false, dbVersion: Long = 1) : LocalDataSource {
        override val databaseVersion: Long = dbVersion
        private var _hasData = hasData
        var storedCountries: List<Country> = emptyList()
        var storedDetails: List<CountryDetails> = emptyList()
        val countriesBySection = mutableMapOf<String, List<Country>>()
        var lastSearchQuery: String? = null
        var searchResult: List<Country> = emptyList()
        var favoritesList: MutableList<Country> = mutableListOf()
        var migrationCalled = false

        fun addFakeSections(vararg sectionIds: String) {
            sectionIds.forEach { id ->
                countriesBySection[id] = listOf(
                    country("${id}_1"),
                    country("${id}_2"),
                    country("${id}_3")
                )
            }
        }

        override suspend fun hasCountriesData() = _hasData
        override suspend fun setCountriesList(list: List<Country>) {
            storedCountries = list
            _hasData = true
        }

        override suspend fun setCountryDetailsList(list: List<CountryDetails>) {
            storedDetails = list
        }
        override suspend fun getCountryDetailsById(id: String) = null
        override suspend fun getCountriesBySection(
            sectionId: String,
            limit: Long
        ): List<Country> =
            countriesBySection[sectionId].orEmpty()

        override suspend fun getAllCountriesBySectionId(sectionId: String) = countriesBySection[sectionId].orEmpty()

        override suspend fun searchCountries(query: String): List<Country> {
            lastSearchQuery = query
            return searchResult
        }
        override suspend fun getAllCountriesWithDetails() = emptyList<CountryWithDetails>()
        override suspend fun isFavorite(id: String) = favoritesList.any { it.id == id }
        override suspend fun addFavorite(id: String) { favoritesList.add(favoritesList.first { it.id == id }) }
        override suspend fun removeFavorite(id: String) { favoritesList.removeAll { it.id == id } }
        override suspend fun getFavorites() = favoritesList.toList()
        override suspend fun resetAndMigrate() {
            migrationCalled = true
        }
    }

    class FakeRemoteDataSource(
        private val result: DataResult<Pair<List<Country>, List<CountryDetails>>> = successApiResult
    ) : RemoteDataSource {
        var callCount = 0
        override suspend fun fetchAllCountriesData(): DataResult<Pair<List<Country>, List<CountryDetails>>> {
            callCount++
            return result
        }
    }

    class FakeAssetsDataSource(
        private val coatOfArmsUrl: String = "https://coat.png"
    ) : AssetsDataSource {
        private fun testAllCountries() = DataResult.Success(listOf(country))
        private fun testAllCountryDetails() = DataResult.Success(
            listOf(details.copy(coatOfArmsPngUrl = coatOfArmsUrl))
        )
        override suspend fun readAllCountries() = testAllCountries()
        override suspend fun readAllCountryDetails() = testAllCountryDetails()
    }

    class FakePlatformInfoProvider : PlatformInfoProvider {
        override fun getAppInfo() = AppInfo(
            appVersion = "test",
            platformName = "testPlatform",
            osVersion = "testOs",
            deviceModel = "testDevice"
        )
    }

    class TestDispatchersProvider : DispatchersProvider.Abstract(
        uiDispatcher = Dispatchers.Unconfined,
        ioDispatcher = Dispatchers.Unconfined,
        defaultDispatcher = Dispatchers.Unconfined
    )

    fun createRepository(
        localDb: LocalDataSource = FakeLocalDataSource(),
        webservices: RemoteDataSource = FakeRemoteDataSource(),
        assets: AssetsDataSource = FakeAssetsDataSource(),
        apiSyncTimestamp: Long = 0L
    ): Repository {
        val settings = MapSettings()
        val repo = Repository(
            localDb = localDb,
            webservices = webservices,
            assetsDataSource = assets,
            settings = settings,
            platformInfo = FakePlatformInfoProvider(),
            dispatchers = TestDispatchersProvider()
        )
        if (apiSyncTimestamp > 0L) {
            repo.localSettings.apiSyncTimestamp = apiSyncTimestamp
        }
        return repo
    }

    fun country(id: String) =
         country.copy(
            id = id,
            name = id
        )
}

class FailingLocalDataSource(dbVersion: Long = 1) : TestFakes.FakeLocalDataSource(dbVersion = dbVersion) {
    override suspend fun resetAndMigrate() {
        throw RuntimeException("DB crash")
    }
}