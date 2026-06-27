package com.exploramus.data.network

import com.exploramus.core.common.result.DataResult
import com.exploramus.data.common.RemoteDataSource
import com.exploramus.data.network.api.CountryApi
import com.exploramus.data.network.dto.CountryRawDto
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class RemoteDataSourceImplTest {

    @Test
    fun `returns success when api succeeds`() = runTest {
        val result = makeSource(NetworkResult.Success(listOf(validDto))).fetchAllCountriesData()
        assertTrue(result is DataResult.Success)
    }

    @Test
    fun `returns error when api fails`() = runTest {
        val result = makeSource(NetworkResult.Error(NetworkError.Unknown)).fetchAllCountriesData()
        assertTrue(result is DataResult.Error)
    }

    @Test
    fun `deduplicates by id`() = runTest {
        val result = makeSource(NetworkResult.Success(listOf(validDto, validDto.copy(id = "FRA"))))
            .fetchAllCountriesData() as DataResult.Success
        assertEquals(1, result.data.size)
    }

    private fun makeSource(apiResult: NetworkResult<List<CountryRawDto>>): RemoteDataSource {
        val mockApi = object : CountryApi {
            override suspend fun fetchExample() = apiResult
            override suspend fun fetchAllRaw() = NetworkResult.Success(emptyList<CountryRawDto>())
        }
        return RemoteDataSourceImpl(mockApi)
    }

    private val validDto = CountryRawDto(id = "FRA", languages = listOf("French"))
}

