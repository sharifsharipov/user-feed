package com.example.userfeed.core.network

import com.example.userfeed.core.constants.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(): HttpClient {
    return HttpClient {
        defaultRequest {
            url(ApiConstants.BASE_URL)
            contentType(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(HttpTimeout) {
            connectTimeoutMillis = ApiConstants.CONNECT_TIMEOUT_MS
            requestTimeoutMillis = ApiConstants.REQUEST_TIMEOUT_MS
            socketTimeoutMillis = ApiConstants.SOCKET_TIMEOUT_MS
        }

        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = ApiConstants.MAX_RETRIES)
            retryOnException(maxRetries = ApiConstants.MAX_RETRIES, retryOnTimeout = true)
            exponentialDelay(base = ApiConstants.RETRY_DELAY_MS.toDouble())
        }

        install(Logging) {
            level = LogLevel.BODY
        }
    }
}
