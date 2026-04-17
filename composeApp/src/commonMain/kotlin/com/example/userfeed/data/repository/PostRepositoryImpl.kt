package com.example.userfeed.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.userfeed.core.utils.AppError
import com.example.userfeed.core.utils.DispatcherProvider
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.core.utils.safeApiCall
import com.example.userfeed.core.utils.safeDbCall
import com.example.userfeed.data.mapper.toDomain
import com.example.userfeed.data.remote.ApiService
import com.example.userfeed.db.UserFeedDatabase
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PostRepositoryImpl(
    private val apiService: ApiService,
    private val database: UserFeedDatabase,
    private val dispatcher: DispatcherProvider
) : PostRepository {

    private val queries = database.userFeedDatabaseQueries

    override fun getPostsByUser(userId: Long): Flow<List<Post>> {
        val postsFlow = queries.getPostsByUserId(userId).asFlow().mapToList(dispatcher.io)
        val favoritesFlow = queries.getAllFavorites().asFlow().mapToList(dispatcher.io)

        return postsFlow.combine(favoritesFlow) { posts, favorites ->
            val favoriteIds = favorites.map { it.postId }.toSet()
            posts.map { entity -> entity.toDomain(isFavorite = entity.id in favoriteIds) }
        }
    }

    override suspend fun refreshPostsByUser(userId: Long): Resource<Unit> =
        safeApiCall(dispatcher.io) {
            val posts = apiService.getPostsByUser(userId)
            queries.transaction {
                queries.deletePostsByUserId(userId)
                posts.forEach { dto ->
                    queries.insertPost(
                        id = dto.id,
                        userId = dto.userId,
                        title = dto.title,
                        body = dto.body
                    )
                }
            }
        }

    override suspend fun getPostById(postId: Long): Resource<Post> {
        val result = safeDbCall(dispatcher.io) {
            queries.getPostById(postId).executeAsOneOrNull()
        }
        return when (result) {
            is Resource.Failure -> result
            is Resource.Success -> {
                val entity = result.data ?: return Resource.Failure(AppError.NotFoundError())
                val isFav = safeDbCall(dispatcher.io) {
                    queries.isFavorite(entity.id).executeAsOne() > 0
                }.getOrNull() ?: false
                Resource.Success(entity.toDomain(isFavorite = isFav))
            }
        }
    }
}
