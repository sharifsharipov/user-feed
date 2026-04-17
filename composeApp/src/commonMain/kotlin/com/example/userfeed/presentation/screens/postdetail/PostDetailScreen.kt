package com.example.userfeed.presentation.screens.postdetail

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.userfeed.domain.model.Comment
import com.example.userfeed.presentation.components.AppTopBar
import com.example.userfeed.presentation.components.UiStateScaffold
import com.example.userfeed.presentation.theme.AppColor
import com.example.userfeed.presentation.theme.AppTextStyle

data class PostDetailScreen(val postId: Long) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<PostDetailScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(postId) { screenModel.loadPostDetail(postId) }

        UiStateScaffold(
            state = state,
            topBar = { AppTopBar(title = "Post Detail", onBackClick = { navigator.pop() }) },
            onRetry = { screenModel.loadPostDetail(postId) }
        ) { detail ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = detail.post.title,
                        style = AppTextStyle.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = detail.post.body, style = AppTextStyle.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Comments (${detail.comments.size})",
                        style = AppTextStyle.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(detail.comments, key = { it.id }) { comment ->
                    CommentCard(comment = comment)
                }
            }
        }
    }
}

@Composable
private fun CommentCard(comment: Comment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = comment.name,
                style = AppTextStyle.titleSmall,
                fontWeight = FontWeight.Bold,
                color = AppColor.primary
            )
            Text(
                text = comment.email,
                style = AppTextStyle.labelSmall,
                color = AppColor.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = comment.body, style = AppTextStyle.bodySmall)
        }
    }
}
