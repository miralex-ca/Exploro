package com.muralex.myapp.datalayer.sources.webservices.apis

import com.muralex.myapp.datalayer.objects.CountryListData
import com.muralex.myapp.datalayer.sources.webservices.ApiClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun ApiClient.fetchCountriesList(): CountriesListResponse? {
    return getResponse("/dkmpl/")
}


@Serializable
data class CountriesListResponse(
    @SerialName("data") val data : List<CountryListData>,
    @SerialName("err") val error : String? = null,
)