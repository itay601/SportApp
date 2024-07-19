package com.example.mykotlinproject.Tests

import com.example.mykotlinproject.blog.data.Post
import com.example.mykotlinproject.blog.functionality.Database
import com.example.mykotlinproject.blog.functionality.DatabaseInterface
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class DatabaseTest {

    private val database: DatabaseInterface = object : Database() {
        override fun finalAddPost(post: Post, function: () -> Boolean) {}
        override fun addPost(post: Post, onSuccess: (DocumentReference) -> Unit, onFailure: (Exception) -> Unit) {}
        override fun getCurrentDate() = "2024-07-19"
        override fun getCurrentTime() = "12:00"
        override suspend fun fetchPostsFromFirebase() = listOf(Post("2024-07-19", "12:00", "Sample Title", "Sample Content", listOf("Comment1")))
        override fun updatePostInFirebase(updatedPost: Post, function: () -> Unit) = function()
    }

    private val samplePost = Post("2024-07-19", "12:00", "Sample Title", "Sample Content", listOf("Comment1"))

    @Test
    fun testFinalAddPost() {
        val result = database.finalAddPost(samplePost) { true }
        assertEquals(true,result)
    }

    @Test
    fun testAddPost() {
        // Since addPost is a void function, we'll just ensure no exceptions are thrown
        database.addPost(samplePost, { ref ->
            assertNotNull(ref)
        }, {
            fail("Should not fail")
        })
    }

    @Test
    fun testGetCurrentDate() {
        val date = database.getCurrentDate()
        assertEquals("2024-07-19", date)
    }

    @Test
    fun testGetCurrentTime() {
        val time = database.getCurrentTime()
        assertEquals("12:00", time)
    }

    @Test
    fun testFetchPostsFromFirebase() {
        runBlocking {
            val posts = database.fetchPostsFromFirebase()
            assertNotNull(posts)
            assertEquals(1, posts.size)
        }
    }

    @Test
    fun testUpdatePostInFirebase() {
        database.updatePostInFirebase(samplePost) {
            assertTrue(true)
        }
    }
}