package com.example.mykotlinproject.Tests

import android.annotation.SuppressLint


import com.example.mykotlinproject.kalories.domain.FetchApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchApiTest {

    private lateinit var fetchApi: FetchApi
    private val sampleResponse = """
        [
            {"name": "Hamburger", "calories": 295, "protein": 17, "fat": 12, "carbohydrates": 33},
            {"name": "Fries", "calories": 312, "protein": 3, "fat": 15, "carbohydrates": 42}
        ]
    """

    @Before
    fun setUp() {
        fetchApi = @SuppressLint("StaticFieldLeak")
        object : FetchApi() {
            override fun fetchFoodApi(query_: String): String {
                return sampleResponse
            }
        }
    }

    @Test
    fun testFetchFoodApi() {
        val response = fetchApi.fetchFoodApi("1lb hamburger and fries")
        assertNotNull(response)
        assertTrue(response.isNotEmpty())
    }

    @Test
    fun testJsonStringToList() {
        fetchApi.fetchFoodApi("1lb hamburger and fries")
        val foodList = fetchApi.jsonStringToList()
        assertNotNull(foodList)
        assertEquals(2, foodList.size)
    }

    @Test
    fun testExecute() {
        fetchApi.execute("1lb hamburger and fries")
        assertNotNull(fetchApi.yourList.value)
        assertEquals(2, fetchApi.yourList.value?.size)
    }
}