package com.example.userfeed.presentation.screens.userlist

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.userfeed.core.utils.UiState
import com.example.userfeed.domain.model.User
import com.example.userfeed.domain.usecase.GetUsersUseCase
import com.example.userfeed.domain.usecase.RefreshUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UserListScreenModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase
) : ScreenModel {

    private val _state = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    val state: StateFlow<UiState<List<User>>> = _state.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        observeUsers()
        refreshUsers()
    }

    private fun observeUsers() {
        screenModelScope.launch {
            getUsersUseCase()
                .catch { e -> _state.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { users ->
                    if (users.isNotEmpty()) {
                        _state.value = UiState.Success(users)
                    }
                }
        }
    }

    fun refreshUsers() {
        screenModelScope.launch {
            _isRefreshing.value = true
            refreshUsersUseCase().onFailure { error ->
                if (_state.value !is UiState.Success) {
                    _state.value = UiState.Error(error.message)
                }
            }
            _isRefreshing.value = false
        }
    }
}
