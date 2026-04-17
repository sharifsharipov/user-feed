package com.example.userfeed.presentation.screens.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.userfeed.domain.usecase.RefreshUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SplashState {
    data object Loading : SplashState
    data object NavigateToUsers : SplashState
    data class Error(val message: String) : SplashState
}

class SplashScreenModel(
    private val refreshUsersUseCase: RefreshUsersUseCase
) : ScreenModel {

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    val state: StateFlow<SplashState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        screenModelScope.launch {
            _state.value = SplashState.Loading
            refreshUsersUseCase().fold(
                onSuccess = { _state.value = SplashState.NavigateToUsers },
                onFailure = { error -> _state.value = SplashState.Error(error.message) }
            )
        }
    }

    fun retry() = loadInitialData()
}
