package com.example.userfeed.domain.model

data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
    val isFavorite: Boolean = false
)
