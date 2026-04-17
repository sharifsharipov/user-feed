package com.example.userfeed.domain.usecase

import com.example.userfeed.fake.FakeData
import com.example.userfeed.fake.FakeFavoriteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ToggleFavoriteUseCaseTest {

    private val repository = FakeFavoriteRepository()
    private val useCase = ToggleFavoriteUseCase(repository)

    @Test
    fun addsPostToFavorites() = runTest {
        val post = FakeData.posts.first()

        val result = useCase(post)

        assertTrue(result.isSuccess)
        val favorites = repository.getFavorites().first()
        assertEquals(1, favorites.size)
        assertEquals(post.id, favorites.first().id)
        assertTrue(favorites.first().isFavorite)
    }

    @Test
    fun removesPostFromFavorites() = runTest {
        val post = FakeData.posts.first()
        useCase(post)
        useCase(post)

        val favorites = repository.getFavorites().first()
        assertTrue(favorites.isEmpty())
    }

    @Test
    fun togglesMultiplePosts() = runTest {
        useCase(FakeData.posts[0])
        useCase(FakeData.posts[1])

        val favorites = repository.getFavorites().first()
        assertEquals(2, favorites.size)

        useCase(FakeData.posts[0])
        val updated = repository.getFavorites().first()
        assertEquals(1, updated.size)
        assertEquals(FakeData.posts[1].id, updated.first().id)
    }
}
