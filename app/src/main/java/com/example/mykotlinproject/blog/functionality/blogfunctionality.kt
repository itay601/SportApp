package com.example.mykotlinproject.blog.functionality

import android.util.Log
import com.example.mykotlinproject.blog.data.Post
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Database { // Class name changed for clarity

    private val db = FirebaseFirestore.getInstance()

    fun addPostToFirebase(post: Post, onSuccess: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("posts")
            .add(post)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                // Handle error
            }
    }

    fun finalAddPost(post:Post){
        addPost(post,
            onSuccess = { documentReference ->
                Log.d("FirestoreHelper", "DocumentSnapshot added with ID: ${documentReference.id}")
            },
            onFailure = { exception ->
                Log.w("FirestoreHelper", "Error adding document", exception)
            }
        )
    }

    fun addPost(
        post: Post,
        onSuccess: (DocumentReference) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("posts")
            .add(post)
            .addOnSuccessListener { documentReference ->
                onSuccess(documentReference)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun createPost(title: String, content: String, comments: List<String>, onSuccess: (DocumentReference) -> Unit, onFailure: (Exception) -> Unit) {
        val currentDate = getCurrentDate()
        val currentTime = getCurrentTime()

        val post = Post(
            date = currentDate,
            time = currentTime,
            title = title,
            content = content,
            comments = comments
        )

        addPost(post, onSuccess, onFailure)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(Date())
    }


}

fun main() {

}