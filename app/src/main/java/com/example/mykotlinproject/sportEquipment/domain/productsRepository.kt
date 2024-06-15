package com.example.mykotlinproject.sportEquipment.domain
import arrow.core.Either

interface ProductsRepository{
    suspend fun getProducts(): Either <NetworkError,List<Product>>
}