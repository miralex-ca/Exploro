package com.muralex.myapp.datalayer.sources.webservices.apis

import com.muralex.myapp.datalayer.objects.CountryExtraData
import com.muralex.myapp.datalayer.sources.webservices.ApiClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun ApiClient.fetchCountryExtraData(country: String): CountryExtraResponse? {
    return getResponse("/dkmpd/"+country.replace(" ","_"))
}

@Serializable
data class CountryExtraResponse(
    @SerialName("data") val data : CountryExtraData,
    @SerialName("err") val error : String? = null,
)