package com.example.userfeed.data.mapper

import com.example.userfeed.data.remote.dto.CommentDto
import com.example.userfeed.data.remote.dto.PostDto
import com.example.userfeed.data.remote.dto.UserDto
import com.example.userfeed.db.FavoritePostEntity
import com.example.userfeed.db.PostEntity
import com.example.userfeed.db.UserEntity
import com.example.userfeed.domain.model.Comment
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.model.User

fun UserDto.toDomain() = User(
    id = id,
    name = name,
    username = username,
    email = email,
    phone = phone,
    website = website
)

fun PostDto.toDomain(isFavorite: Boolean = false) = Post(
    id = id,
    userId = userId,
    title = title,
    body = body,
    isFavorite = isFavorite
)

fun CommentDto.toDomain() = Comment(
    id = id,
    postId = postId,
    name = name,
    email = email,
    body = body
)

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    username = username,
    email = email,
    phone = phone,
    website = website
)

fun PostEntity.toDomain(isFavorite: Boolean = false) = Post(
    id = id,
    userId = userId,
    title = title,
    body = body,
    isFavorite = isFavorite
)

fun FavoritePostEntity.toDomain() = Post(
    id = postId,
    userId = userId,
    title = title,
    body = body,
    isFavorite = true
)
