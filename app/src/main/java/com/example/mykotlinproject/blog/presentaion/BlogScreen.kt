// blog/presentation/BlogPage.kt
package com.example.mykotlinproject.blog.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mykotlinproject.blog.data.Post
import com.example.mykotlinproject.blog.functionality.Database
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "RememberReturnType")
@Preview
@Composable
fun BlogPage() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "blog_list") {
        composable("blog_list") { BlogList(navController) }
        composable("add_post") { AddPostPage(navController) }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogList(navController: NavHostController) {
    val posts1 = remember {
        mutableStateListOf(
            Post(
                "2024-07-13",
                "10:00 AM",
                "Running Tips",
                "Discuss and share your running tips!",
                listOf("Great tips!", "Very helpful!")
            ),
            Post(
                "2024-07-14",
                "11:00 AM",
                "Healthy Eating",
                "Share your favorite healthy recipes.",
                listOf("Love this!", "Yummy!")
            ),
            Post(
                "2024-07-15",
                "12:00 PM",
                "Workout Routines",
                "Talk about different workout routines.",
                listOf("Awesome routines!", "Thanks for sharing!")
            )
        )
    }
    val posts = remember { mutableStateListOf<Post>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val database = Database()
        coroutineScope.launch {
            val fetchedPosts = database.fetchPostsFromFirebase()
            posts.clear()
            posts.addAll(fetchedPosts)
        }
    }
    var selectedPost by remember { mutableStateOf<Post?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Blog Topics") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(color = 0xFF7E2222))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("add_post") }) {
            Text(text = "Create New Topic")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (selectedPost == null) {
            LazyColumn {
                items(posts) { post ->
                    TopicCard(post = post) {
                        selectedPost = post
                    }
                }
                items(posts1) { post ->
                    TopicCard(post = post) {
                        selectedPost = post
                    }
                }

            }
        } else {
            PostDetails(post = selectedPost!!)
        }
    }
}

@Composable
fun TopicCard(post: Post, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = post.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = post.content, fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun PostDetails(post: Post) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = post.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Date: ${post.date}", fontSize = 16.sp, color = Color.Gray)
        Text(text = "Time: ${post.time}", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = post.content, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Comments:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(post.comments) { comment ->
                CommentCard(comment = comment)
            }
        }
    }
}

@Composable
fun CommentCard(comment: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = comment, fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}


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
    }
}
