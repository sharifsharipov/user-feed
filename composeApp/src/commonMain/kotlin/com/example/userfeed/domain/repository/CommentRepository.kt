package com.example.userfeed.domain.repository

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Comment

interface CommentRepository {
    suspend fun getCommentsByPost(postId: Long): Resource<List<Comment>>
}
