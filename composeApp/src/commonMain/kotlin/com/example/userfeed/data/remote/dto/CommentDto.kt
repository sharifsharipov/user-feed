package com.example.userfeed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val postId: Long,
    val id: Long,
    val name: String,
    val email: String,
    val body: String
)
