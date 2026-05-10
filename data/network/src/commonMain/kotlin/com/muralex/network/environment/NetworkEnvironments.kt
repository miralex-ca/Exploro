package com.muralex.network.environment

object NetworkEnvironments {
    val PROD = NetworkEnvironment(
        name = "Production",
        countriesBaseUrl = "https://restcountries.com",
        apiKey = ""
    )
}

