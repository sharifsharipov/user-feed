package com.example.userfeed.domain.usecase

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Comment
import com.example.userfeed.domain.repository.CommentRepository

class GetCommentsUseCase(private val repository: CommentRepository) {
    suspend operator fun invoke(postId: Long): Resource<List<Comment>> =
        repository.getCommentsByPost(postId)
}
