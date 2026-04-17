package com.example.userfeed.domain.usecase

import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.repository.UserRepository

class HasUsersUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): Resource<Boolean> = repository.hasUsers()
}
