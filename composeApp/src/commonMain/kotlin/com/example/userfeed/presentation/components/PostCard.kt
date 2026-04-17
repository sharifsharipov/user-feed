package com.example.userfeed.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.userfeed.domain.model.Post
import com.example.userfeed.presentation.theme.AppColor
import com.example.userfeed.presentation.theme.AppTextStyle

@Composable
fun PostCard(
    post: Post,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    showBody: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = post.title,
                    style = AppTextStyle.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (showBody) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = post.body,
                        style = AppTextStyle.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = AppColor.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = onFavoriteClick) {
                Text(
                    text = if (post.isFavorite) "\u2B50" else "\u2606",
                    style = AppTextStyle.titleLarge
                )
            }
        }
    }
}
