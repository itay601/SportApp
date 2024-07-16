package com.example.mykotlinproject.blog.functionality

import com.example.mykotlinproject.blog.data.Post
import com.google.firebase.firestore.DocumentReference

interface DatabaseInterface {

    fun finalAddPost(post: Post, function: () -> Boolean)

    fun addPost(
        post: Post,
        onSuccess: (DocumentReference) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun getCurrentDate(): String

    fun getCurrentTime(): String

    suspend fun fetchPostsFromFirebase(): List<Post>
}