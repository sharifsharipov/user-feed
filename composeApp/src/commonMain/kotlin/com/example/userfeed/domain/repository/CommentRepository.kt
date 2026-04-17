package com.example.userfeed.domain.repository

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentsByPost(postId: Long): Flow<List<Comment>>
    suspend fun refreshComments(postId: Long): Resource<Unit>
}
