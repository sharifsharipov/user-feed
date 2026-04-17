package com.example.userfeed.domain.usecase

import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: FavoriteRepository) {

    operator fun invoke(): Flow<List<Post>> = repository.getFavorites()
}
