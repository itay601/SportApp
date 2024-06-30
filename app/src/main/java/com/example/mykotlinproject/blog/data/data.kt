package com.example.mykotlinproject.blog.data

data class Post(
    val date: String,
    val time: String,
    val title: String,
    val content: String,
    val comments:List<String>
)
