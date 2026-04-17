package com.example.userfeed.domain.usecase

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.repository.FavoriteRepository

class ToggleFavoriteUseCase(private val repository: FavoriteRepository) {

    suspend operator fun invoke(post: Post): Resource<Unit> = repository.toggleFavorite(post)
}
