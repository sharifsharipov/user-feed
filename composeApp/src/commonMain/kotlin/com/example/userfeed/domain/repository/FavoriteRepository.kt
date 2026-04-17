package com.example.userfeed.domain.repository

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<Post>>
    suspend fun toggleFavorite(post: Post): Resource<Unit>
    suspend fun isFavorite(postId: Long): Resource<Boolean>
}
