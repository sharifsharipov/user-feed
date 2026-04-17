package com.example.userfeed.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.userfeed.core.utils.DispatcherProvider
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.core.utils.safeApiCall
import com.example.userfeed.data.mapper.toDomain
import com.example.userfeed.data.remote.ApiService
import com.example.userfeed.db.UserFeedDatabase
import com.example.userfeed.domain.model.Comment
import com.example.userfeed.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CommentRepositoryImpl(
    private val apiService: ApiService,
    private val database: UserFeedDatabase,
    private val dispatcher: DispatcherProvider
) : CommentRepository {

    private val queries = database.userFeedDatabaseQueries

    override fun getCommentsByPost(postId: Long): Flow<List<Comment>> =
        queries.getCommentsByPostId(postId)
            .asFlow()
            .mapToList(dispatcher.io)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun refreshComments(postId: Long): Resource<Unit> =
        safeApiCall(dispatcher.io) {
            val comments = apiService.getCommentsByPost(postId)
            queries.transaction {
                queries.deleteCommentsByPostId(postId)
                comments.forEach { dto ->
                    queries.insertComment(
                        id = dto.id,
                        postId = dto.postId,
                        name = dto.name,
                        email = dto.email,
                        body = dto.body
                    )
                }
            }
        }
}
