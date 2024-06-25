package com.example.mykotlinproject.kalories.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinproject.kalories.data.FoodNutrition
import com.google.gson.Gson
//import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection
import java.net.URL


//import androidx.lifecycle.viewModelScope

//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext



open class FetchApi : ViewModel () {

    private val YOUR_API_KEY = "qiYla7B0GBqPSrhi5vV9Vg==u8PHD6wfrKrM8Vzc"
    private var response: String? = null

    // MutableLiveData for your list
    private val _yourList = MutableLiveData<List<FoodNutrition>>()

    // Exposed as LiveData
    val yourList: LiveData<List<FoodNutrition>>
        get() = _yourList

    fun fetchFoodApi(query_: String = "1lb hamburger and fries") {
        val YOUR_API_KEY = this.YOUR_API_KEY
        val query = query_
        val apiUrl = "https://api.api-ninjas.com/v1/nutrition?query=${
            query.replace(
                " ",
                "%20"
            )
        }" // Encode the query
        println(apiUrl)

        val maxRetries = 3
        var attempt = 0
        var success = false

        while (attempt < maxRetries && !success) {
            var connection: HttpURLConnection? = null
            try {
                val url = URL(apiUrl)
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("X-Api-Key", YOUR_API_KEY)

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    println(response)
                    this.response = response
                    Log.d("response","${this.response}")
                    success = true
                } else {
                    val errorResponse =
                        connection.errorStream?.bufferedReader()?.use { it.readText() }
                            ?: "No error details"
                    println("Error: $responseCode $errorResponse")
                    if (responseCode == 502) {
                        println("Retrying... Attempt ${attempt + 1}")
                    } else {
                        break
                    }
                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
                break
            } finally {
                connection?.disconnect()
            }
            attempt++
        }

        if (!success) {
            println("Failed to fetch data after $maxRetries attempts")
        }
    }

    fun jsonStringToList(): List<FoodNutrition> {
        val gson = Gson()
        val listType = object : TypeToken<List<FoodNutrition>>() {}.type
        _yourList.value = gson.fromJson(this.response, listType)
        println(_yourList)
        return gson.fromJson(this.response, listType)
    }

}















