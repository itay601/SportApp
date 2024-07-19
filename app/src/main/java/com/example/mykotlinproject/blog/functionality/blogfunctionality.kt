package com.example.mykotlinproject.blog.functionality

import android.util.Log
import com.example.mykotlinproject.blog.data.Post
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


open class Database : DatabaseInterface { // Class name changed for clarity

    private val db = FirebaseFirestore.getInstance()


    override fun finalAddPost(post: Post, function: () -> Boolean) {
        addPost(post,
            onSuccess = { documentReference ->
                Log.d("FirestoreHelper", "DocumentSnapshot added with ID: ${documentReference.id}")
            },
            onFailure = { exception ->
                Log.w("FirestoreHelper", "Error adding document", exception)
            }
        )
    }

    override fun addPost(
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


    override fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(Date())
    }

    override suspend fun fetchPostsFromFirebase(): List<Post> {
        return try {
            val snapshot = db.collection("posts").get().await()
            snapshot.documents.map { document ->
                Post(
                    date = document.getString("date") ?: "",
                    time = document.getString("time") ?: "",
                    title = document.getString("title") ?: "",
                    content = document.getString("content") ?: "",
                    comments = document.get("comments") as List<String>? ?: emptyList()
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    override fun updatePostInFirebase(updatedPost: Post, function: () -> Unit){
        db.collection("posts").document(updatedPost.title)
            .set(updatedPost)
            .addOnSuccessListener {
                Log.d("FirestoreHelper", "DocumentSnapshot successfully updated!")
                function()
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreHelper", "Error updating document", e)
            }
    }
}

