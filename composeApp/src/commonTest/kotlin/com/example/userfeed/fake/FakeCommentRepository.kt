package com.example.userfeed.fake

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Comment
import com.example.userfeed.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCommentRepository : CommentRepository {

    private val commentsFlow = MutableStateFlow<List<Comment>>(emptyList())
    var refreshResult: Resource<Unit> = Resource.Success(Unit)

    override fun getCommentsByPost(postId: Long): Flow<List<Comment>> = commentsFlow

    override suspend fun refreshComments(postId: Long): Resource<Unit> {
        if (refreshResult is Resource.Success) {
            commentsFlow.value = FakeData.comments.filter { it.postId == postId }
        }
        return refreshResult
    }
}
