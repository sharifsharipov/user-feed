package com.example.userfeed.domain.repository

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun refreshUsers(): Resource<Unit>
}
