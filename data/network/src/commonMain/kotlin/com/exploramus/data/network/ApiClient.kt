package com.exploramus.data.network

import com.exploramus.core.common.logging.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

class ApiClient(
   val client: HttpClient = defaultClient()
) {
    companion object {
        fun create(): ApiClient = ApiClient(defaultClient())

        internal fun withEngine(client: HttpClient): ApiClient = ApiClient(client)

        fun defaultClient() = HttpClient {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 15_000
            }
            expectSuccess = true
        }
    }

    suspend inline fun <reified T> get(
        url: String,
        headers: Map<String, String> = emptyMap()
    ): NetworkResult<T> {
        return try {
            val response = client.get(url) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
            val body = response.body<T>()
            Log.d("KTOR SUCCESS GET $url - Status: ${response.status}")
            NetworkResult.Success(body)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("KTOR FAILED GET $url - Status: ${e.message}")
            NetworkResult.Error(
                error = e.toNetworkError()
            )
        }
    }
}