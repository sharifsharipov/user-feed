package com.example.userfeed.domain.usecase

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.repository.PostRepository

class GetPostByIdUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postId: Long): Resource<Post> = repository.getPostById(postId)
}
