package com.example.userfeed.domain.repository

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPostsByUser(userId: Long): Flow<List<Post>>
    suspend fun refreshPostsByUser(userId: Long): Resource<Unit>
    suspend fun getPostById(postId: Long): Resource<Post>
}
