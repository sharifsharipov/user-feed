package com.example.userfeed.presentation.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.userfeed.presentation.components.AppTopBar
import com.example.userfeed.presentation.components.PostCard
import com.example.userfeed.presentation.components.UiStateScaffold
import com.example.userfeed.presentation.screens.postdetail.PostDetailScreen
import com.example.userfeed.presentation.theme.AppColor
import com.example.userfeed.presentation.theme.AppTextStyle

class FavoritesScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<FavoritesScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        UiStateScaffold(
            state = state,
            topBar = { AppTopBar(title = "Favorites", onBackClick = { navigator.pop() }) },
            onRetry = {}
        ) { favorites ->
            if (favorites.isEmpty()) {
                Text(
                    text = "No favorites yet",
                    style = AppTextStyle.bodyLarge,
                    color = AppColor.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(favorites, key = { it.id }) { post ->
                        PostCard(
                            post = post,
                            showBody = true,
                            onClick = { navigator.push(PostDetailScreen(post.id)) },
                            onFavoriteClick = { screenModel.removeFavorite(post) }
                        )
                    }
                }
            }
        }
    }
}
