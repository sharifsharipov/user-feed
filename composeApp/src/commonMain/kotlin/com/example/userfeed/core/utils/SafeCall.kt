package com.example.userfeed.core.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> T
): Resource<T> = withContext(dispatcher) {
    try {
        Resource.Success(block())
    } catch (e: IOException) {
        Resource.Failure(AppError.NetworkError())
    } catch (e: ServerResponseException) {
        Resource.Failure(AppError.ServerError(code = e.response.status.value))
    } catch (e: ClientRequestException) {
        Resource.Failure(AppError.ServerError(code = e.response.status.value))
    } catch (e: Exception) {
        Resource.Failure(AppError.UnknownError(e.message ?: "Noma'lum xatolik"))
    }
}

suspend fun <T> safeDbCall(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> T
): Resource<T> = withContext(dispatcher) {
    try {
        Resource.Success(block())
    } catch (e: Exception) {
        Resource.Failure(AppError.DatabaseError(e.message ?: "DB xatoligi"))
    }
}
