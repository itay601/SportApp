package com.example.mykotlinproject.kalories.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinproject.kalories.data.FoodNutrition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors

open class FetchApi : ViewModel() {

    private val YOUR_API_KEY = "qiYla7B0GBqPSrhi5vV9Vg==u8PHD6wfrKrM8Vzc"

    private var Response: String? = null
    // MutableLiveData for your list
    private val _yourList = MutableLiveData<List<FoodNutrition>>()
    // Exposed as LiveData
    val yourList: LiveData<List<FoodNutrition>>
        get() = _yourList




    open fun fetchFoodApi(query_: String = "1lb hamburger and fries") :String {
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
            //Log.d("apifetch", "before try fetch api")
            try {
                val url = URL(apiUrl)
                connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("X-Api-Key", YOUR_API_KEY)

                val responseCode = connection.responseCode
                //Log.d("apifetch", "response code $responseCode")
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    this.Response = response
                    println(this.Response)
                    println(response.javaClass)
                    //Log.d("response", "very good ,$Response")
                    success = true
                    return response
                } else {
                    val errorResponse =
                        connection.errorStream?.bufferedReader()?.use { it.readText() }
                            ?: "No error details"
                    println("Error: $responseCode $errorResponse")
                    if (responseCode == 502) {
                        println("Retrying... Attempt ${attempt + 1}")
                        //Log.d("retry", "retry..")
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
        return ""
    }


    fun jsonStringToList(): List<FoodNutrition> {
        val gson = Gson()
        val listType = object : TypeToken<List<FoodNutrition>>() {}.type

        println(listType.javaClass)

        if (this.Response!=null){
            try{
                this._yourList.postValue(gson.fromJson(this.Response, listType))
            }catch(e:Exception){
                println("err: $e")
            }
            println(this._yourList.value)
            println(this._yourList)
            return gson.fromJson(this.Response, listType)
        }
        else{
            _yourList.value = emptyList()
            return emptyList()
        }
    }

     fun execute(query: String = "1lb hamburger and fries"){
        val callable = Callable {
            val l = fetchFoodApi(query_ = query)
            val k = jsonStringToList()
            println(k)
            return@Callable k
        }

        val executorService = Executors.newSingleThreadExecutor()
        val future = executorService.submit(callable)
        try {
            val result = future.get()
              // Blocks until the task is completed and returns the result
            println("Callable result: $result")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            executorService.shutdown()
        }
    }
}








