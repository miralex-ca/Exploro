package com.exploramus.data.network.environment

import com.exploramus.data.network.BuildKonfig

object NetworkEnvironments {
    val PROD = NetworkEnvironment(
        name = "Production",
        countriesBaseUrl = "https://api.restcountries.com/countries/v5",
        apiKey = BuildKonfig.RESTCOUNTRIES_API_KEY
    )

    val TEST = NetworkEnvironment(
        name = "Test",
        countriesBaseUrl = "https://www.example.com/",
        apiKey = "APY_KEY"
    )

}
