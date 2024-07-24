package com.example.mykotlinproject.home.data

data class ApiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Exercise>
)

data class Exercise(
    val id: Int,
    val uuid: String,
    val name: String,
    val exercise_base: Int,
    val description: String,
    val created: String,
    val category: Int,
    val muscles: List<Int>,
    val muscles_secondary: List<Int>,
    val equipment: List<Int>,
    val language: Int,
    val license: Int,
    val license_author: String,
    val variations: List<Int>,
    val author_history: List<Int>
)