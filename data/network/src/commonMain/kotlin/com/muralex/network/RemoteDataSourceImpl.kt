package com.muralex.network

import com.muralex.data.common.RemoteDataSource

import com.muralex.models.Country
import com.muralex.models.CountryExtraInfo
import com.muralex.network.dto.CountryExtraDto
import com.muralex.network.dto.CountryListDto
import com.muralex.network.dto.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RemoteDataSourceImpl : RemoteDataSource {
    private val apiClient = ApiClient()

    override suspend fun fetchCountriesList(): List<Country>? {
        val response = apiClient.fetchCountriesList() ?: return null
        if (response.error != null) {
           // debugLogger.log("ERROR MESSAGE: ${response.error}")
            return null
        }
        return response.data.entity()
    }

    override suspend fun fetchCountryExtraData(country: String): CountryExtraInfo? {
        val response = apiClient.fetchCountryExtraData(country) ?: return null
        if (response.error != null) {
            // debugLogger.log("ERROR MESSAGE: ${response.error}")
            return null
        }
        return response.data.entity
    }
}


@Serializable
data class CountriesListResponse(
    @SerialName("data") val data : List<CountryListDto>,
    @SerialName("err") val error : String? = null,
)

suspend fun ApiClient.fetchCountriesList(): CountriesListResponse? {
    return getResponse("/dkmpl/")
}


suspend fun ApiClient.fetchCountryExtraData(country: String): CountryExtraResponse? {
    return getResponse("/dkmpd/"+country.replace(" ","_"))
}

@Serializable
data class CountryExtraResponse(
    @SerialName("data") val data : CountryExtraDto,
    @SerialName("err") val error : String? = null,
)