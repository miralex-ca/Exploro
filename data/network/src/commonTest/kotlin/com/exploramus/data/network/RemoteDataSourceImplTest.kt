package com.exploramus.data.network

import com.exploramus.core.common.result.DataResult
import com.exploramus.data.common.RemoteDataSource
import com.exploramus.data.network.api.CountryApi
import com.exploramus.data.network.dto.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class RemoteDataSourceImplTest {

    @Test
    fun `returns success when api succeeds`() = runTest {
        val source = makeSource(NetworkResult.Success(listOf(validDto)))
        val result = source.fetchAllCountriesData()
        assertTrue(result is DataResult.Success)
    }

    @Test
    fun `returns error when api fails`() = runTest {
        val source = makeSource(NetworkResult.Error(NetworkError.Unknown))
        val result = source.fetchAllCountriesData()
        assertTrue(result is DataResult.Error)
    }

    @Test
    fun `countries and details counts match`() = runTest {
        val source = makeSource(NetworkResult.Success(listOf(validDto)))
        val result = (source.fetchAllCountriesData() as DataResult.Success).data
        assertEquals(result.first.size, result.second.size)
    }

    @Test
    fun `filters out blank alpha3`() = runTest {
        val blankDto = validDto.copy(codes = CountryCodesDto(alpha3 = ""))
        val source = makeSource(NetworkResult.Success(listOf(validDto, blankDto)))
        val result = (source.fetchAllCountriesData() as DataResult.Success).data
        assertEquals(1, result.first.size)
    }

    @Test
    fun `deduplicates by alpha3`() = runTest {
        val duplicate = validDto.copy(names = CountryNamesDto("France Duplicate", ""))
        val source = makeSource(NetworkResult.Success(listOf(validDto, duplicate)))
        val result = (source.fetchAllCountriesData() as DataResult.Success).data
        assertEquals(1, result.first.size)
    }

    private fun makeSource(apiResult: NetworkResult<List<CountryRawDto>>): RemoteDataSource {
        val mockApi = object : CountryApi {
            override suspend fun fetchAllRaw() = apiResult
        }
        return RemoteDataSourceImpl(mockApi)
    }

    private val validDto = CountryRawDto(
        codes = CountryCodesDto(alpha3 = "FRA"),
        names = CountryNamesDto(common = "France", official = "French Republic"),
        flag = CountryFlagDto(emoji = "🇫🇷", urlPng = "", urlSvg = ""),
        capitals = listOf(CountryCapitalDto("Paris", CountryCapitalCoordinatesDto(48.87, 2.33))),
        continents = listOf("Europe"),
        subregion = "Western Europe",
        area = CountryAreaDto(551695.0),
        population = 69081996,
        languages = listOf(CountryLanguageDto("fra", "French", "Français")),
        currencies = listOf(CountryCurrencyDto("EUR", "Euro", "€")),
        links = CountryLinksDto("", "", "https://en.wikipedia.org/wiki/France"),
        timezones = listOf("UTC+01:00")
    )
}

