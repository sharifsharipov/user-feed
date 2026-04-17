package com.example.userfeed.presentation.screens.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.userfeed.core.utils.UiState
import com.example.userfeed.domain.model.User
import com.example.userfeed.presentation.components.AppTopBar
import com.example.userfeed.presentation.components.ErrorContent
import com.example.userfeed.presentation.components.LoadingContent
import com.example.userfeed.presentation.screens.favorites.FavoritesScreen
import com.example.userfeed.presentation.screens.postlist.PostListScreen
import com.example.userfeed.presentation.theme.AppColor
import com.example.userfeed.presentation.theme.AppTextStyle

class UserListScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<UserListScreenModel>()
        val state by screenModel.state.collectAsState()
        val isRefreshing by screenModel.isRefreshing.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Users",
                    actions = {
                        IconButton(onClick = { navigator.push(FavoritesScreen()) }) {
                            Text(text = "\u2B50", style = AppTextStyle.titleLarge)
                        }
                    }
                )
            }
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
                            onRefresh = screenModel::refreshUsers
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                items(currentState.data, key = { it.id }) { user ->
                                    UserItem(
                                        user = user,
                                        onClick = { navigator.push(PostListScreen(user.id, user.name)) }
                                    )
                                }
                            }
                        }
                    }
                    is UiState.Error -> {
                        ErrorContent(
                            message = currentState.message,
                            onRetry = screenModel::refreshUsers
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserItem(user: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = user.name,
                style = AppTextStyle.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                style = AppTextStyle.bodyMedium,
                color = AppColor.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
