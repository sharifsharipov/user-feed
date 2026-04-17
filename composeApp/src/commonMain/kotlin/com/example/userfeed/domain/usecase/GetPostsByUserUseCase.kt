package com.example.userfeed.domain.usecase

import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsByUserUseCase(private val repository: PostRepository) {
    operator fun invoke(userId: Long): Flow<List<Post>> = repository.getPostsByUser(userId)
}
