package com.example.mykotlinproject.sportEquipment.data

import com.example.mykotlinproject.sportEquipment.domain.Product
import retrofit2.http.GET;

interface ProductApi{

    @GET("https://fakestoreapi.com/products")
    suspend fun getProducts(): List<Product>

}