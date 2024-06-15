package com.example.mykotlinproject.sportEquipment.domain

data class Product(
    val id: Int,
    val title: String,
    val price:Double,
    val description: String,
    val category: String,
    val image:String
)

data class Rating(
    val rage:Double,
    val count:Int
)