package com.example.userfeed.domain.usecase

import com.example.userfeed.core.utils.AppError
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.fake.FakeUserRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RefreshUsersUseCaseTest {

    private val repository = FakeUserRepository()
    private val useCase = RefreshUsersUseCase(repository)

    @Test
    fun returnsSuccessOnRefresh() = runTest {
        val result = useCase()
        assertTrue(result.isSuccess)
    }

    @Test
    fun returnsFailureOnNetworkError() = runTest {
        repository.refreshResult = Resource.Failure(AppError.NetworkError())

        val result = useCase()

        assertTrue(result.isFailure)
        result.onFailure { error ->
            assertTrue(error is AppError.NetworkError)
        }
    }

    @Test
    fun callsRepositoryRefresh() = runTest {
        useCase()
        useCase()

        assertEquals(2, repository.refreshCallCount)
    }
}
