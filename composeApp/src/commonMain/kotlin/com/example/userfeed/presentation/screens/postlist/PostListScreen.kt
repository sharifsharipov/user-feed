package com.example.userfeed.presentation.screens.postlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.userfeed.core.utils.UiState
import com.example.userfeed.presentation.components.AppTopBar
import com.example.userfeed.presentation.components.ErrorContent
import com.example.userfeed.presentation.components.LoadingContent
import com.example.userfeed.presentation.components.PostCard
import com.example.userfeed.presentation.screens.postdetail.PostDetailScreen

data class PostListScreen(
    val userId: Long,
    val userName: String
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<PostListScreenModel>()
        val state by screenModel.state.collectAsState()
        val isRefreshing by screenModel.isRefreshing.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(userId) { screenModel.loadPosts(userId) }

        Scaffold(
            topBar = { AppTopBar(title = userName, onBackClick = { navigator.pop() }) }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                when (val currentState = state) {
                    is UiState.Loading -> LoadingContent()
                    is UiState.Success -> {
                        PullToRefreshBox(
                            isRefreshing = isRefreshing,
                            onRefresh = screenModel::refreshPosts
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                items(currentState.data, key = { it.id }) { post ->
                                    PostCard(
                                        post = post,
                                        onClick = { navigator.push(PostDetailScreen(post.id)) },
                                        onFavoriteClick = { screenModel.toggleFavorite(post) }
                                    )
                                }
                            }
                        }
                    }
                    is UiState.Error -> {
                        ErrorContent(
                            message = currentState.message,
                            onRetry = screenModel::refreshPosts
                        )
                    }
                }
            }
        }
    }
}
