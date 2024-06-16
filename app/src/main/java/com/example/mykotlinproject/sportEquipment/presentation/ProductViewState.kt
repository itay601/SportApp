package com.example.mykotlinproject.sportEquipment.presentation

import com.example.mykotlinproject.sportEquipment.domain.Product

data class ProductViewState(
    val isLaoding: Boolean = false,
    val prodacts:List<Product> = emptyList(),
    val error: String? =null
)