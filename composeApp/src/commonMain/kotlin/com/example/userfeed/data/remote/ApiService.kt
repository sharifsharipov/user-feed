package com.example.userfeed.data.remote

import com.example.userfeed.data.remote.dto.CommentDto
import com.example.userfeed.data.remote.dto.PostDto
import com.example.userfeed.data.remote.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val client: HttpClient) {

    suspend fun getUsers(): List<UserDto> =
        client.get("/users").body()

    suspend fun getPostsByUser(userId: Long): List<PostDto> =
        client.get("/posts") {
            parameter("userId", userId)
        }.body()

    suspend fun getCommentsByPost(postId: Long): List<CommentDto> =
        client.get("/comments") {
            parameter("postId", postId)
        }.body()
}
