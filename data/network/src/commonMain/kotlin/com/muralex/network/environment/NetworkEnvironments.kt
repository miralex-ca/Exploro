package com.muralex.network.environment

import com.muralex.data.network.BuildKonfig

object NetworkEnvironments {
    val PROD = NetworkEnvironment(
        name = "Production",
        countriesBaseUrl = "https://restcountries.com",
        apiKey = BuildKonfig.RESTCOUNTRIES_API_KEY
    )
}
