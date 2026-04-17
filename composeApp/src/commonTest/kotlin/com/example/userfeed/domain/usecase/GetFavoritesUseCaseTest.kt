package com.example.userfeed.domain.usecase

import com.example.userfeed.fake.FakeData
import com.example.userfeed.fake.FakeFavoriteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetFavoritesUseCaseTest {

    private val repository = FakeFavoriteRepository()
    private val toggleUseCase = ToggleFavoriteUseCase(repository)
    private val useCase = GetFavoritesUseCase(repository)

    @Test
    fun returnsEmptyListInitially() = runTest {
        val result = useCase().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun returnsFavoritesAfterToggle() = runTest {
        toggleUseCase(FakeData.posts[0])
        toggleUseCase(FakeData.posts[1])

        val result = useCase().first()
        assertEquals(2, result.size)
    }
}
