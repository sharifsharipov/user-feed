package com.example.userfeed.presentation.screens.postdetail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.userfeed.core.utils.UiState
import com.example.userfeed.domain.model.Comment
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.usecase.GetCommentsUseCase
import com.example.userfeed.domain.usecase.GetPostByIdUseCase
import com.example.userfeed.domain.usecase.RefreshCommentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class PostDetailState(
    val post: Post,
    val comments: List<Comment> = emptyList()
)

class PostDetailScreenModel(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val refreshCommentsUseCase: RefreshCommentsUseCase
) : ScreenModel {

    private val _state = MutableStateFlow<UiState<PostDetailState>>(UiState.Loading)
    val state: StateFlow<UiState<PostDetailState>> = _state.asStateFlow()

    fun loadPostDetail(postId: Long) {
        screenModelScope.launch {
            _state.value = UiState.Loading

            getPostByIdUseCase(postId).fold(
                onSuccess = { post ->
                    observeComments(post, postId)
                    refreshCommentsUseCase(postId)
                },
                onFailure = { error ->
                    _state.value = UiState.Error(error.message)
                }
            )
        }
    }

    private fun observeComments(post: Post, postId: Long) {
        screenModelScope.launch {
            getCommentsUseCase(postId)
                .catch { _state.value = UiState.Success(PostDetailState(post)) }
                .collect { comments ->
                    _state.value = UiState.Success(PostDetailState(post, comments))
                }
        }
    }
}
