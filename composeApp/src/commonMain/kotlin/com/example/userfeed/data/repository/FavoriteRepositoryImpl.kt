package com.example.userfeed.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.userfeed.core.utils.DispatcherProvider
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.core.utils.safeDbCall
import com.example.userfeed.data.mapper.toDomain
import com.example.userfeed.db.UserFeedDatabase
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val database: UserFeedDatabase,
    private val dispatcher: DispatcherProvider
) : FavoriteRepository {

    private val queries = database.userFeedDatabaseQueries

    override fun getFavorites(): Flow<List<Post>> =
        queries.getAllFavorites()
            .asFlow()
            .mapToList(dispatcher.io)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun toggleFavorite(post: Post): Resource<Unit> =
        safeDbCall(dispatcher.io) {
            val isFav = queries.isFavorite(post.id).executeAsOne() > 0
            if (isFav) {
                queries.deleteFavorite(post.id)
            } else {
                queries.insertFavorite(
                    postId = post.id,
                    userId = post.userId,
                    title = post.title,
                    body = post.body
                )
            }
        }

    override suspend fun isFavorite(postId: Long): Resource<Boolean> =
        safeDbCall(dispatcher.io) {
            queries.isFavorite(postId).executeAsOne() > 0
        }
}
