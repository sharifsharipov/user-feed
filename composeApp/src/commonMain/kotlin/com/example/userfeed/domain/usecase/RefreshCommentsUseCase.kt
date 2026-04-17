package com.example.userfeed.domain.usecase

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.repository.CommentRepository

class RefreshCommentsUseCase(private val repository: CommentRepository) {
    suspend operator fun invoke(postId: Long): Resource<Unit> =
        repository.refreshComments(postId)
}
