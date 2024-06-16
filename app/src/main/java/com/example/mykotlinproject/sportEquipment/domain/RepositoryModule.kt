package com.example.mykotlinproject.sportEquipment.domain

import com.example.mykotlinproject.sportEquipment.data.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindProductsRepository(impl:ProductRepositoryImpl) :ProductsRepository
}