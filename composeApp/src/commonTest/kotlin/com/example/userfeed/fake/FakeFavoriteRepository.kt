package com.example.userfeed.fake

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeFavoriteRepository : FavoriteRepository {

    private val favoritesFlow = MutableStateFlow<List<Post>>(emptyList())

    override fun getFavorites(): Flow<List<Post>> = favoritesFlow

    override suspend fun toggleFavorite(post: Post): Resource<Unit> {
        val current = favoritesFlow.value.toMutableList()
        val existing = current.find { it.id == post.id }
        if (existing != null) {
            current.remove(existing)
        } else {
            current.add(post.copy(isFavorite = true))
        }
        favoritesFlow.value = current
        return Resource.Success(Unit)
    }

    override suspend fun isFavorite(postId: Long): Resource<Boolean> {
        val isFav = favoritesFlow.value.any { it.id == postId }
        return Resource.Success(isFav)
    }
}
