package com.example.mykotlinproject.blog.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mykotlinproject.blog.data.Post
import com.example.mykotlinproject.blog.functionality.Database


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostPage(navController: NavHostController) {
    val title = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }
    val comments = remember { mutableStateOf("") }

    val db = Database()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Add New Post") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(color = 0xFF7E2222))
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = content.value,
            onValueChange = { content.value = it },
            label = { Text(text = "Content") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = comments.value,
            onValueChange = { comments.value = it },
            label = { Text(text = "Comments (comma separated)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val post = Post(
                date = db.getCurrentDate(),  // You can set the current date dynamically if needed
                time = db.getCurrentTime(),  // You can set the current time dynamically if needed
                title = title.value,
                content = content.value,
                comments = comments.value.split(",").map { it.trim() }
            )
            db.finalAddPost(post) {
                navController.popBackStack()
            }
        }) {
            Text(text = "Add Post")
        }
        Button(onClick = { navController.navigate("blog_list") }) {
            Text(text = "Return To Blog")
        }
    }
}
