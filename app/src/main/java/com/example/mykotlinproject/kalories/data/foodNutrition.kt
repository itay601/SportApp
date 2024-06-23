package com.example.mykotlinproject.kalories.data

import kotlinx.serialization.Serializable

@Serializable
data class FoodNutrition(
    val name: String,
    val fat_total_g: Double,
    val fat_saturated_g: Double,
    val sodium_mg: Int,
    val potassium_mg: Int,
    val cholesterol_mg: Int,
    val carbohydrates_total_g: Double,
    val fiber_g: Double,
    val sugar_g: Double
)

