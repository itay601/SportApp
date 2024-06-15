package com.example.mykotlinproject.sportEquipment.data

import com.example.mykotlinproject.sportEquipment.domain.ApiError
import com.example.mykotlinproject.sportEquipment.domain.NetworkError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): NetworkError{
    val error = when(this){
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }
    return NetworkError(
        error = error,
        t = this
        )
}