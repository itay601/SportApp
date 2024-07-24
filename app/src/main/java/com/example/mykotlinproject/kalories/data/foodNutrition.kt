package com.example.mykotlinproject.kalories.data

data class FoodNutrition(
    val name: String,
    val fiber_g: Double,
    val sugar_g: Double,
    val sodium_mg: Double,
    val fat_total_g: Double,
    val carbohydrates_total_g: Double,
    val cholesterol_mg: Double,
    val fat_saturated_g: Double,
    val potassium_mg: Int
)