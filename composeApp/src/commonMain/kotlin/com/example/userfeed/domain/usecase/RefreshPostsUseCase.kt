package com.example.userfeed.domain.usecase

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.repository.PostRepository

class RefreshPostsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(userId: Long): Resource<Unit> = repository.refreshPostsByUser(userId)
}
