package com.example.userfeed.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.userfeed.core.utils.DispatcherProvider
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.core.utils.safeApiCall
import com.example.userfeed.data.mapper.toDomain
import com.example.userfeed.data.remote.ApiService
import com.example.userfeed.db.UserFeedDatabase
import com.example.userfeed.domain.model.User
import com.example.userfeed.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val database: UserFeedDatabase,
    private val dispatcher: DispatcherProvider
) : UserRepository {

    private val queries = database.userFeedDatabaseQueries

    override fun getUsers(): Flow<List<User>> =
        queries.getAllUsers()
            .asFlow()
            .mapToList(dispatcher.io)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun refreshUsers(): Resource<Unit> =
        safeApiCall(dispatcher.io) {
            val users = apiService.getUsers()
            queries.transaction {
                users.forEach { dto ->
                    queries.insertUser(
                        id = dto.id,
                        name = dto.name,
                        username = dto.username,
                        email = dto.email,
                        phone = dto.phone,
                        website = dto.website
                    )
                }
            }
        }
}
