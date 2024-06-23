package com.example.mykotlinproject.kalories.domain

import android.annotation.SuppressLint
import com.example.mykotlinproject.kalories.data.FoodNutrition
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class FetchApi {

    private val YOUR_API_KEY = "qiYla7B0GBqPSrhi5vV9Vg==u8PHD6wfrKrM8Vzc"
    var response :String = ""


    @SuppressLint("NewApi")
    fun fetchFoodApi(query_: String = "1lb hamburger and fries"): String {
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
                    success = true
                    return this.response
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
        val fail = "fail"

        if (!success) {
            println("Failed to fetch data after $maxRetries attempts")

            return fail
        }
        return fail
    }
    fun jsonNutritionToDataClass(jsonString: String): List<FoodNutrition> {
        val json = Json { ignoreUnknownKeys = true }
        val foodList = json.decodeFromString<List<FoodNutrition>>(jsonString)

        foodList.forEach { println(it) }
        return foodList
    }



}


fun main() {
    val fetchApi = FetchApi()
    val response = fetchApi.fetchFoodApi()
    var i=0
    val length =response.length
    while (i<length){
        println(response[i])
        i++
    }
    //val a = fetchApi.jsonNutritionToDataClass(response)

}





