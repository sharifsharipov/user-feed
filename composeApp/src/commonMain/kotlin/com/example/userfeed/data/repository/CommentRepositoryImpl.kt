package com.example.userfeed.data.repository

import com.example.userfeed.core.utils.DispatcherProvider
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.core.utils.safeApiCall
import com.example.userfeed.data.mapper.toDomain
import com.example.userfeed.data.remote.ApiService
import com.example.userfeed.domain.model.Comment
import com.example.userfeed.domain.repository.CommentRepository

class CommentRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: DispatcherProvider
) : CommentRepository {

    override suspend fun getCommentsByPost(postId: Long): Resource<List<Comment>> =
        safeApiCall(dispatcher.io) {
            apiService.getCommentsByPost(postId).map { it.toDomain() }
        }
}
