package com.example.mykotlinproject.kalories.domain

import junit.framework.TestCase.assertNotNull
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28]) // You can specify the SDK version here
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
        fetchApi = FetchApi()
    }

    @Test
    fun testFetchFoodApi() {
        val response = fetchApi.fetchFoodApi("1lb hamburger and fries")
        println(response)
        Assert.assertNotNull(response)
        Assert.assertTrue(response.isNotEmpty())
    }

    @Test
    fun testJsonStringToList() {
        fetchApi.fetchFoodApi("1lb hamburger and fries")
        val foodList = fetchApi.jsonStringToList()
        println(foodList)
        Assert.assertNotNull(foodList)
        Assert.assertEquals(2, foodList.size)
    }

    @Test
    fun testExecute() {
        val apiResponse = fetchApi.execute()

        // Debug information
        println("API Response: $apiResponse")

        // Ensure response is not null
        assertNotNull("API response should not be null", apiResponse)

        // Additional assertions based on expected response
        // assertTrue("Expected condition message", condition)
    }
}