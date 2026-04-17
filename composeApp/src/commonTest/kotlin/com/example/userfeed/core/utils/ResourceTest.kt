package com.example.userfeed.core.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ResourceTest {

    @Test
    fun foldReturnsSuccessValue() {
        val resource: Resource<String> = Resource.Success("data")

        val result = resource.fold(
            onSuccess = { "got: $it" },
            onFailure = { "error" }
        )

        assertEquals("got: data", result)
    }

    @Test
    fun foldReturnsFailureValue() {
        val resource: Resource<String> = Resource.Failure(AppError.NetworkError())

        val result = resource.fold(
            onSuccess = { "got: $it" },
            onFailure = { "error: ${it.message}" }
        )

        assertTrue(result.startsWith("error:"))
    }

    @Test
    fun mapTransformsSuccessData() {
        val resource: Resource<Int> = Resource.Success(42)

        val mapped = resource.map { it.toString() }

        assertEquals("42", mapped.getOrNull())
    }

    @Test
    fun mapPreservesFailure() {
        val resource: Resource<Int> = Resource.Failure(AppError.ServerError(code = 500))

        val mapped = resource.map { it.toString() }

        assertTrue(mapped.isFailure)
    }

    @Test
    fun flatMapChainsSuccess() {
        val resource: Resource<Int> = Resource.Success(42)

        val result = resource.flatMap { Resource.Success("value: $it") }

        assertEquals("value: 42", result.getOrNull())
    }

    @Test
    fun flatMapShortCircuitsOnFailure() {
        val resource: Resource<Int> = Resource.Failure(AppError.DatabaseError())

        val result = resource.flatMap { Resource.Success("value: $it") }

        assertTrue(result.isFailure)
    }

    @Test
    fun getOrNullReturnsDataOnSuccess() {
        val resource: Resource<String> = Resource.Success("data")
        assertEquals("data", resource.getOrNull())
    }

    @Test
    fun getOrNullReturnsNullOnFailure() {
        val resource: Resource<String> = Resource.Failure(AppError.UnknownError())
        assertNull(resource.getOrNull())
    }

    @Test
    fun onSuccessExecutesAction() {
        var called = false
        val resource: Resource<String> = Resource.Success("data")

        resource.onSuccess { called = true }

        assertTrue(called)
    }

    @Test
    fun onFailureExecutesAction() {
        var errorMessage = ""
        val resource: Resource<String> = Resource.Failure(AppError.NotFoundError("not found"))

        resource.onFailure { errorMessage = it.message }

        assertEquals("not found", errorMessage)
    }
}
