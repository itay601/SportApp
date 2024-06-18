package com.example.mykotlinproject.blog.functionality

import com.example.mykotlinproject.blog.data.Post

suspend fun fetchPostsFromDatabase(): List<Post> {
    // Your implementation to fetch posts from MongoDB
    return listOf(
        Post("Sample Title 1", "Sample Content 1","11"),
        Post("Sample Title 2", "Sample Content 2","12")
    )
}