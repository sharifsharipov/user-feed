package com.example.userfeed.presentation.screens.favorites

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.userfeed.core.utils.UiState
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.usecase.GetFavoritesUseCase
import com.example.userfeed.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoritesScreenModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ScreenModel {

    private val _state = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Post>>> = _state.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        screenModelScope.launch {
            getFavoritesUseCase()
                .catch { e -> _state.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { favorites -> _state.value = UiState.Success(favorites) }
        }
    }

    fun removeFavorite(post: Post) {
        screenModelScope.launch { toggleFavoriteUseCase(post) }
    }
}
