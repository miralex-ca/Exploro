package com.muralex.data.sources.webservices.apis

import com.muralex.data.objects.CountryExtraDto
import com.muralex.data.sources.webservices.ApiClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun ApiClient.fetchCountryExtraData(country: String): CountryExtraResponse? {
    return getResponse("/dkmpd/"+country.replace(" ","_"))
}

@Serializable
data class CountryExtraResponse(
    @SerialName("data") val data : CountryExtraDto,
    @SerialName("err") val error : String? = null,
)