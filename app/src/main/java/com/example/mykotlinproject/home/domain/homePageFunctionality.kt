package com.example.mykotlinproject.home.domain



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinproject.home.data.Exercise
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors


/* curl -X GET https://wger.de/api/v2/exercise/ \
     -H 'Authorization: Token 39b15b9447ff2834f326355b8d16cf030a4f571a' */



//val json1

open class FetchApi2 : ViewModel() {

    private val YOUR_API_KEY = "39b15b9447ff2834f326355b8d16cf030a4f571a"

    private var response: String? = null
    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>>
        get() = _exerciseList

    open fun fetchExerciseApi(): String {
        val apiUrl = "https://wger.de/api/v2/exercise/"
        Log.d("API_URL", apiUrl)

        val maxRetries = 3
        var attempt = 0
        var success = false

        while (attempt < maxRetries && !success) {
            var connection: HttpURLConnection? = null
            try {
                val url = URL(apiUrl)
                connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "Token $YOUR_API_KEY")

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    this.response = response
                    Log.d("API_Response", this.response ?: "")
                    success = true
                    return response
                } else {
                    val errorResponse =
                        connection.errorStream?.bufferedReader()?.use { it.readText() }
                            ?: "No error details"
                    Log.e("API_Error", "Error: $responseCode $errorResponse")
                    if (responseCode == 502) {
                        Log.d("API_Retry", "Retrying... Attempt ${attempt + 1}")
                    } else {
                        break
                    }
                }
            } catch (e: Exception) {
                Log.e("API_Exception", "Exception: ${e.message}")
                break
            } finally {
                connection?.disconnect()
            }
            attempt++
        }

        if (!success) {
            Log.e("API_Failure", "Failed to fetch data after $maxRetries attempts")
        }
        return ""
    }

    fun jsonStringToList(): List<Exercise> {
        val gson = Gson()
        val listType = object : TypeToken<List<Exercise>>() {}.type

        if (this.response != null) {
            try {
                this._exerciseList.postValue(gson.fromJson(this.response, listType))
            } catch (e: Exception) {
                Log.e("API_ParsingError", "Error: $e")
            }
            return gson.fromJson(this.response, listType)
        } else {
            _exerciseList.value = emptyList()
            return emptyList()
        }
    }

    fun execute() {
        val callable = Callable {
            fetchExerciseApi()
            jsonStringToList()
        }

        val executorService = Executors.newSingleThreadExecutor()
        val future = executorService.submit(callable)
        try {
            val result = future.get()
            Log.d("API_CallableResult", "Callable result: $result")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            executorService.shutdown()
        }
    }
}


