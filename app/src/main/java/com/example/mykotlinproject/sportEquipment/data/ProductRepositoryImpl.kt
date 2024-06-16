package com.example.mykotlinproject.sportEquipment.data

import arrow.core.Either
import com.example.mykotlinproject.sportEquipment.domain.NetworkError
import com.example.mykotlinproject.sportEquipment.domain.Product
import com.example.mykotlinproject.sportEquipment.domain.ProductsRepository
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val productApi:ProductApi

) : ProductsRepository{

    override suspend fun getProducts() : Either <NetworkError,List<Product>> {
        //Either -> another way of try and catch
        return Either.catch {
            productApi.getProducts()
        }.mapLeft { it.toNetworkError() }
    }

}