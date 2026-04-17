package com.example.userfeed.fake

import com.example.userfeed.core.utils.AppError
import com.example.userfeed.core.utils.Resource
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePostRepository : PostRepository {

    private val postsFlow = MutableStateFlow<List<Post>>(emptyList())
    var refreshResult: Resource<Unit> = Resource.Success(Unit)
    var getByIdResult: Resource<Post> = Resource.Success(FakeData.posts.first())

    override fun getPostsByUser(userId: Long): Flow<List<Post>> = postsFlow

    override suspend fun refreshPostsByUser(userId: Long): Resource<Unit> {
        if (refreshResult is Resource.Success) {
            postsFlow.value = FakeData.posts.filter { it.userId == userId }
        }
        return refreshResult
    }

    override suspend fun getPostById(postId: Long): Resource<Post> = getByIdResult

    fun emitPosts(posts: List<Post>) {
        postsFlow.value = posts
    }
}
