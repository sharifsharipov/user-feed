package com.example.userfeed.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.userfeed.core.utils.UiState

@Composable
fun <T> UiStateScaffold(
    state: UiState<T>,
    topBar: @Composable () -> Unit,
    onRetry: () -> Unit,
    content: @Composable (T) -> Unit
) {
    Scaffold(topBar = topBar) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is UiState.Loading -> LoadingContent()
                is UiState.Error -> ErrorContent(message = state.message, onRetry = onRetry)
                is UiState.Success -> content(state.data)
            }
        }
    }
}
