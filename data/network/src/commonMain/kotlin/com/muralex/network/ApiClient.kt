package com.muralex.network


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class ApiClient {
    val client = HttpClient {

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 15_000
        }

        expectSuccess = false
    }

    suspend inline fun <reified T> get(url: String): NetworkResult<T> {
        return try {
            val response = client.get(url)

            if (!response.status.isSuccess()) {
                return NetworkResult.Error(
                    message = "HTTP ${response.status.value}"
                )
            }

            val body = response.body<T>()
            NetworkResult.Success(body)

        } catch (e: Exception) {
            NetworkResult.Error(
                message = e.message ?: "Unknown error",
                throwable = e
            )
        }
    }
}