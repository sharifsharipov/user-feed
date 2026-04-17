package com.example.userfeed.fake

import com.example.userfeed.core.utils.AppError
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.User
import com.example.userfeed.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserRepository : UserRepository {

    private val usersFlow = MutableStateFlow<List<User>>(emptyList())
    var refreshResult: Resource<Unit> = Resource.Success(Unit)
    var refreshCallCount = 0
        private set

    override fun getUsers(): Flow<List<User>> = usersFlow

    override suspend fun refreshUsers(): Resource<Unit> {
        refreshCallCount++
        if (refreshResult is Resource.Success) {
            usersFlow.value = FakeData.users
        }
        return refreshResult
    }

    fun emitUsers(users: List<User>) {
        usersFlow.value = users
    }
}
