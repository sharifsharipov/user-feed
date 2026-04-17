package com.example.userfeed.fake

import com.example.userfeed.domain.model.Comment
import com.example.userfeed.domain.model.Post
import com.example.userfeed.domain.model.User

object FakeData {

    val users = listOf(
        User(id = 1, name = "John Doe", username = "johndoe", email = "john@test.com", phone = "123", website = "john.com"),
        User(id = 2, name = "Jane Smith", username = "janesmith", email = "jane@test.com", phone = "456", website = "jane.com"),
        User(id = 3, name = "Bob Wilson", username = "bobwilson", email = "bob@test.com", phone = "789", website = "bob.com")
    )

    val posts = listOf(
        Post(id = 1, userId = 1, title = "First Post", body = "First post body"),
        Post(id = 2, userId = 1, title = "Second Post", body = "Second post body"),
        Post(id = 3, userId = 2, title = "Third Post", body = "Third post body")
    )

    val comments = listOf(
        Comment(id = 1, postId = 1, name = "Commenter 1", email = "c1@test.com", body = "Great post!"),
        Comment(id = 2, postId = 1, name = "Commenter 2", email = "c2@test.com", body = "Nice work!"),
        Comment(id = 3, postId = 2, name = "Commenter 3", email = "c3@test.com", body = "Interesting")
    )
}
