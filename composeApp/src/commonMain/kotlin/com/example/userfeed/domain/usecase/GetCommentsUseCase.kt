package com.example.userfeed.domain.usecase

import com.example.userfeed.domain.model.Comment
import com.example.userfeed.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow

class GetCommentsUseCase(private val repository: CommentRepository) {
    operator fun invoke(postId: Long): Flow<List<Comment>> =
        repository.getCommentsByPost(postId)
}
