package com.example.mykotlinproject.kalories.domain


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykotlinproject.kalories.data.FoodNutrition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL


open class FetchApi : ViewModel() {

    private val YOUR_API_KEY = "qiYla7B0GBqPSrhi5vV9Vg==u8PHD6wfrKrM8Vzc"
    private var Response: String? = null

    // MutableLiveData for your list
    private val _yourList = MutableLiveData<List<FoodNutrition>>()

    // Exposed as LiveData
    val yourList: LiveData<List<FoodNutrition>>
        get() = _yourList



//    fun fetchapi(){
//        val s:StringBuilder = java.lang.StringBuilder()
//        //s.append("1")
//    }


    fun fetchFoodApi(query_: String = "1lb hamburger and fries") {
        viewModelScope.launch {
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
                Log.d("apifetch", "before try fetch api")
                try {

                    val url = URL(apiUrl)
                    connection = withContext(Dispatchers.IO) {
                        url.openConnection()
                    } as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.setRequestProperty("X-Api-Key", YOUR_API_KEY)

                    val responseCode = connection.responseCode
                    Log.d("apifetch", "response code $responseCode")
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val response = connection.inputStream.bufferedReader().use { it.readText() }
                        println(response)
                        Response = response
                        Log.d("response", "$this.response")
                        success = true
                        jsonStringToList()

                    } else {
                        val errorResponse =
                            connection.errorStream?.bufferedReader()?.use { it.readText() }
                                ?: "No error details"
                        println("Error: $responseCode $errorResponse")
                        if (responseCode == 502) {
                            println("Retrying... Attempt ${attempt + 1}")
                            Log.d("retry", "retry..")
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
    }

    fun jsonStringToList(): List<FoodNutrition> {
        Log.d("loginfunjson","entered to fun json")
        val gson = Gson()
        val listType = object : TypeToken<List<FoodNutrition>>() {}.type
        if (this.Response!=null){
            _yourList.value = gson.fromJson(this.Response, listType)
            println(_yourList)
            return gson.fromJson(this.Response, listType)
        }
        else{
            return emptyList()
        }
    }

}











