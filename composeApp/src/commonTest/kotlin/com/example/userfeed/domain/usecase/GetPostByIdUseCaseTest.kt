package com.example.userfeed.domain.usecase

import com.example.userfeed.core.utils.AppError
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.fake.FakeData
import com.example.userfeed.fake.FakePostRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetPostByIdUseCaseTest {

    private val repository = FakePostRepository()
    private val useCase = GetPostByIdUseCase(repository)

    @Test
    fun returnsPostOnSuccess() = runTest {
        val expected = FakeData.posts.first()
        repository.getByIdResult = Resource.Success(expected)

        val result = useCase(expected.id)

        assertTrue(result.isSuccess)
        assertEquals(expected.title, result.getOrNull()?.title)
    }

    @Test
    fun returnsNotFoundOnMissing() = runTest {
        repository.getByIdResult = Resource.Failure(AppError.NotFoundError())

        val result = useCase(999)

        assertTrue(result.isFailure)
        result.onFailure { error ->
            assertTrue(error is AppError.NotFoundError)
        }
    }
}
