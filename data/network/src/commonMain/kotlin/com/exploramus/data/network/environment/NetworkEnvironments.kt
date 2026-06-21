package com.exploramus.data.network.environment

import com.exploramus.data.network.BuildKonfig

object NetworkEnvironments {
    val PROD = NetworkEnvironment(
        name = "Production",
        countriesBaseUrl = "https://api.restcountries.com/countries/v5",
        apiKey = BuildKonfig.RESTCOUNTRIES_API_KEY
    )
}
