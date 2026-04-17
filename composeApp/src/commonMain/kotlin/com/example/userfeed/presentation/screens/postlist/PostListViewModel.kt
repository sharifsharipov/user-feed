package com.example.userfeed.presentation.screens.postlist

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.userfeed.core.utils.UiState
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.usecase.GetPostsByUserUseCase
import com.example.userfeed.domain.usecase.RefreshPostsUseCase
import com.example.userfeed.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PostListScreenModel(
    private val getPostsByUserUseCase: GetPostsByUserUseCase,
    private val refreshPostsUseCase: RefreshPostsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ScreenModel {

    private val _state = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Post>>> = _state.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var currentUserId: Long = -1

    fun loadPosts(userId: Long) {
        if (currentUserId == userId) return
        currentUserId = userId
        observePosts(userId)
        refreshPosts()
    }

    private fun observePosts(userId: Long) {
        screenModelScope.launch {
            getPostsByUserUseCase(userId)
                .catch { e -> _state.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { posts -> _state.value = UiState.Success(posts) }
        }
    }

    fun refreshPosts() {
        screenModelScope.launch {
            _isRefreshing.value = true
            refreshPostsUseCase(currentUserId).onFailure { error ->
                if (_state.value !is UiState.Success) {
                    _state.value = UiState.Error(error.message)
                }
            }
            _isRefreshing.value = false
        }
    }

    fun toggleFavorite(post: Post) {
        screenModelScope.launch { toggleFavoriteUseCase(post) }
    }
}
